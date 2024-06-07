package com.example.ASM.controllers;

import com.example.ASM.models.Order;
import com.example.ASM.models.User;
import com.example.ASM.services.MailService;
import com.example.ASM.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class PayPalController {

    private final PaypalService paypalService;

    // sb-6ctgf31057516@personal.example.com
    @RequestMapping("/payment/paypal")
    public RedirectView payment(@RequestParam("totalPrice") double totalPrice, @RequestParam("userName") String userName) {
        try {
            String cancelUrl = "http://localhost:8080/payment/paypal/cancel";
            String successUrl = "http://localhost:8080/payment/paypal/success";
            Payment payment = paypalService.createPayment(
                    totalPrice,
                    "USD",
                    "PAYPAL",
                    "sale",
                    userName + "Thanh toán",
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        return new RedirectView("payment/paypal/error");
    }

    @GetMapping("/payment/paypal/success")
    public String success(Model model,
                          @RequestParam("paymentId") String paymentId,
                          @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                model.addAttribute("router", "payment.jsp");
                return "index";
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("router", "payment.jsp");
        return "index";
    }


    @GetMapping("/payment/paypal/cancel")
    @ResponseBody
    public String cancel() {
        return "cancel";
    }

    @GetMapping("/payment/paypal/error")
    @ResponseBody
    public String error() {
        return "error";
    }

}
