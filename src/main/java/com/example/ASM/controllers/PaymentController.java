package com.example.ASM.controllers;

import com.example.ASM.dto.CartDto;
import com.example.ASM.enums.OrderStatus;
import com.example.ASM.enums.PaymentMethod;
import com.example.ASM.models.Order;
import com.example.ASM.models.OrderDetail;
import com.example.ASM.models.User;
import com.example.ASM.services.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class PaymentController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/payment")
    @Transactional
    public String payment(Model model, Authentication authentication, @RequestParam("paymentMethod") String paymentMethod) throws MessagingException, ServletException, IOException {

        if (addUserInfoToModel(model, authentication)) {
            User user = userService.findByUsername(authentication.getName());

            if (!isUserInfoValid(user, model)) {
                return "forward:/user-page";
            }

            List<CartDto> cartItems = cartService.findAllByUser(user);
            double totalPrice = calculateTotalPrice(cartItems);
            UUID orderId = createOrder(user, totalPrice, paymentMethod);

            if (orderId == null) {
                model.addAttribute("messageInfo", "Có lỗi xảy ra khi tạo đơn hàng.");
                return "index";
            }
            Order order = orderService.findById(orderId);
            addOrderDetails(orderId, cartItems);

            if (paymentMethod.equalsIgnoreCase("paypal")) {
                return handlePaypalPayment(totalPrice, user.getName());
            } else if (paymentMethod.equalsIgnoreCase("cod")) {
                return handleCodPayment(user.getId(), order.getId());
            } else if (paymentMethod.equalsIgnoreCase("stripe")) {
                return handleStripePayment((long) totalPrice, user.getEmail(), "thu cung");
            }
        }
        model.addAttribute("router", "payment.jsp");
        return "index";
    }

    @GetMapping("/paymentPage")
    public String openPaymentPage(@RequestParam("userId") UUID userId, @RequestParam("orderId") UUID orderId, Model model) throws MessagingException {
        User user = userService.findById(userId);
        Order order = orderService.findById(orderId);
        cartService.removeAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("router", "payment.jsp");
        MailService.sendOrderMail(user, order, mailSender);
        return "index";
    }

    private boolean isUserInfoValid(User user, Model model) {
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            model.addAttribute("messageInfo", "Vui lòng thêm địa chỉ");
            return false;
        } else if (user.getPhone() == null || user.getPhone().isEmpty()) {
            model.addAttribute("messageInfo", "Vui lòng thêm số điện thoại");
            return false;
        }
        return true;
    }

    private double calculateTotalPrice(List<CartDto> cartItems) {
        return cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    private UUID createOrder(User user, double totalPrice, String paymentMethod) {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setUser(user);
        order.setId(orderId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);

        if (paymentMethod.equalsIgnoreCase("paypal")) {
            order.setPaymentMethod(PaymentMethod.PAYPAL);
        } else if (paymentMethod.equalsIgnoreCase("cod")) {
            order.setPaymentMethod(PaymentMethod.COD);
        } else if (paymentMethod.equalsIgnoreCase("stripe")) {
            order.setPaymentMethod(PaymentMethod.STRIPE);
        }

        orderService.addOrder(order);
        return orderId;
    }

    private void addOrderDetails(UUID orderId, List<CartDto> cartItems) {
        for (CartDto cartItem : cartItems) {
            UUID orderDetailId = UUID.randomUUID();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(orderDetailId);
            orderDetail.setOrder(orderService.findById(orderId));
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getPrice() * cartItem.getQuantity());
            orderDetail.setProductId(cartItem.getId());
            orderDetailService.addOrderDetail(orderDetail);
        }
    }

    private String handlePaypalPayment(double totalPrice, String userName) throws UnsupportedEncodingException {
        totalPrice = totalPrice / 25000; // Giả sử đây là tỷ giá quy đổi
        return "redirect:/payment/paypal?totalPrice=" + totalPrice + "&userName=" + URLEncoder.encode(userName, "UTF-8");
    }

    private String handleCodPayment(UUID userId, UUID orderId) throws MessagingException {
        return "redirect:/paymentPage?userId=" + userId + "&orderId=" + orderId;
    }

    private String handleStripePayment(Long amount, String email, String productName) {
        amount = amount / 25000;
        return "redirect:/payment/stripe?amount=" + amount + "&email=" + email + "&productName=" + productName;
    }

    public boolean addUserInfoToModel(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userInfo", userService.findByUsername(authentication.getName()));
            return true;
        } else {
            model.addAttribute("userInfo", "");
            return false;
        }
    }

}
