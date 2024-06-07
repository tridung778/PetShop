package com.example.ASM.controllers;

import com.example.ASM.dto.CartDto;
import com.example.ASM.enums.OrderStatus;
import com.example.ASM.models.*;
import com.example.ASM.services.*;
import com.example.ASM.ultis.PetFilterForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private CartService cartService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    public boolean addUserInfoToModel(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("userInfo", userService.findByUsername(authentication.getName()));
            return true;
        } else {
            model.addAttribute("userInfo", "");
            return false;
        }
    }

    @RequestMapping("/")
    public String index(Model model, Authentication authentication) {
        if (addUserInfoToModel(model, authentication)) {
            model.addAttribute("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        } else {
            model.addAttribute("animalIndex", null);
        }
        model.addAttribute("pets", petService.findAllPet());
        model.addAttribute("router", "home.jsp");
        return "index";
    }

    @RequestMapping("/pets")
    public String openCat(Model model, @RequestParam("petCategory") String petCategory, @RequestParam("p") Optional<Integer> p, Authentication authentication) {
        Pageable page = PageRequest.of(p.orElse(0), 8);
        if (addUserInfoToModel(model, authentication)) {
            model.addAttribute("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        } else {
            model.addAttribute("animalIndex", null);
        }
        Page<Pet> petPage = petService.findAllPetByCategory(petCategory, page);

        double minPrice = 0;
        double maxPrice = 10000000;
        PetFilterForm petFilterForm = new PetFilterForm();
        petFilterForm.setBreeds(petService.findAllBreedsByCategoryName(petCategory));
        petFilterForm.setRange1(minPrice);
        petFilterForm.setRange2(maxPrice);
        model.addAttribute("petSize", petService.findAllPetByCategory(petCategory).size());
        model.addAttribute("petFilterForm", petFilterForm);
        model.addAttribute("pets", petPage.getContent());
        model.addAttribute("breeds", petFilterForm.getBreeds());
        model.addAttribute("router", "listProducts.jsp");

        // Thêm các thuộc tính liên quan đến phân trang vào model
        model.addAttribute("currentPage", page.getPageNumber());
        model.addAttribute("totalPages", petPage.getTotalPages());

        if (petCategory.equalsIgnoreCase("cat")) {
            model.addAttribute("petName", "Mèo");
        } else if (petCategory.equalsIgnoreCase("dog")) {
            model.addAttribute("petName", "Chó");
        }

        return "index";
    }

    @RequestMapping("/filter-sort-pet")
    public String filterAndSortPet(@ModelAttribute PetFilterForm petFilterForm, Model model, @RequestParam("petCategory") String petCategory, @RequestParam("field") Optional<String> field, @RequestParam("order") Optional<String> order, Authentication authentication) {
        if (addUserInfoToModel(model, authentication)) {
            model.addAttribute("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        } else {
            model.addAttribute("animalIndex", null);
        }
        // Cập nhật danh sách breeds cho PetFilterForm
        petFilterForm.setBreeds(petService.findAllBreedsByCategoryName(petCategory));

        // Lấy các giá trị được chọn từ form
        List<String> selectedBreeds = petFilterForm.getSelectedBreeds();
        double minPrice = petFilterForm.getRange1() * 1000000;
        double maxPrice = petFilterForm.getRange2() * 1000000;

        // Xử lý sắp xếp
        String sortField = field.orElse("price");
        Sort.Direction sortDirection = order.orElse("ASC").equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, sortField);

        // Lọc và sắp xếp thú cưng
        List<Pet> filteredAndSortedPets = petService.getPetsByCategoryNameAndBreedInAndPriceBetween(petCategory, selectedBreeds, minPrice, maxPrice, sort);

        // Thiết lập thuộc tính cho model
        model.addAttribute("petSize", petService.findAllPetByCategory(petCategory).size());
        model.addAttribute("petFilterForm", petFilterForm);
        model.addAttribute("pets", filteredAndSortedPets);
        model.addAttribute("breeds", petFilterForm.getBreeds());

        // Điều chỉnh giá trị range1 và range2
        petFilterForm.setRange1(minPrice / 1000000);
        petFilterForm.setRange2(maxPrice / 1000000);

        model.addAttribute("router", "listProducts.jsp");
        if (petCategory.equalsIgnoreCase("cat")) {
            model.addAttribute("petName", "Mèo");
        } else if (petCategory.equalsIgnoreCase("dog")) {
            model.addAttribute("petName", "Chó");
        }

        return "index";
    }

    @RequestMapping(value = "/addToCart/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(Model model, @PathVariable("id") UUID id, @RequestParam("quantity") int quantity, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pet> optionalProduct = petService.findById(id);
        if (addUserInfoToModel(model, authentication)) {
            response.put("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        } else {
            response.put("animalIndex", null);
        }
        if (optionalProduct.isPresent()) {
            Pet pet = optionalProduct.get();
            Optional<CartDto> existingCartItem = cartService.findById(id);
            CartDto cart;

            if (existingCartItem.isPresent()) {
                cart = existingCartItem.get();
                cart.setQuantity(cart.getQuantity() + quantity);
            } else {
                cart = new CartDto();
                if (authentication.getName() != null) {
                    cart.setUser(userService.findByUsername(authentication.getName()));
                }
                cart.setId(id);
                cart.setName(pet.getName());
                cart.setPrice(pet.getPrice());
                cart.setQuantity(quantity);
                cart.setType("Thú Cưng");
                cart.setThumbnail(pet.getImage());
                cart.setSpecie(pet.getBreed());
            }
            cartService.save(cart);
        } else {
            response.put("error", "Pet not found with ID: " + id);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@Validated @RequestParam("id") UUID id, @RequestParam("quantity") int quantity) {

        CartDto cart = cartService.findById(id).orElse(null);
        if (cart != null) {
            cart.setQuantity(quantity);
            cartService.save(cart);
        }

        return "redirect:/openCart";
    }

    @RequestMapping("/remove/{id}")
    public String removeProduct(@PathVariable("id") UUID id) {
        cartService.deleteById(id);
        return "redirect:/openCart";
    }


    @RequestMapping("/openCart")
    public String openCart(Model model, Authentication authentication) {
        if (addUserInfoToModel(model, authentication)) {
            model.addAttribute("cartItems", cartService.findAllByUser(userService.findByUsername(authentication.getName())));
            model.addAttribute("animalIndex", cartService.findAllByUser(userService.findByUsername(authentication.getName())).size());
        } else {
            model.addAttribute("cartItems", null);
            model.addAttribute("animalIndex", null);
        }
        List<CartDto> cartItems = cartService.findAllByUser(userService.findByUsername(authentication.getName()));
        double totalAmount = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("router", "cart.jsp");
        return "index";
    }

    @RequestMapping("/pet-detail/{id}")
    public String petDetail(Model model, @PathVariable("id") UUID id, Authentication authentication) {
        if (addUserInfoToModel(model, authentication)) {
            model.addAttribute("cartItems", cartService.findAllByUser(userService.findByUsername(authentication.getName())));
        } else {
            model.addAttribute("animalIndex", null);
        }
        Pet pet = petService.findPetById(id);
        model.addAttribute("pet", pet);
        model.addAttribute("router", "pet-detail.jsp");
        return "index";
    }

    @RequestMapping("/search")
    public String search(@RequestParam("name-product") String nameProduct) {
        Pet pet = petService.findPetByName(nameProduct);
        return "redirect:/pet-detail/" + pet.getId();
    }

    @RequestMapping("/history")
    public String history(Model model, Authentication authentication) {
        if (addUserInfoToModel(model, authentication)) {
            User user = userService.findByUsername(authentication.getName());
            List<Order> orderList = orderService.findAllByUser(user);

            model.addAttribute("orders", orderList);

            if (!orderList.isEmpty()) {
                Map<UUID, Integer> orderQuantities = new HashMap<>();
                for (Order order : orderList) {
                    int quantity = orderDetailService.getQuantityByOrderId(order.getId());
                    orderQuantities.put(order.getId(), quantity);
                }
                model.addAttribute("orderQuantities", orderQuantities);
            }
        }
        model.addAttribute("router", "history.jsp");
        return "index";
    }

    @RequestMapping("/history/{id}")
    public String historyDetail(Model model, @PathVariable("id") UUID id) {
        List<OrderDetail> orderDetails = orderDetailService.findAllByOrderId(id);
        model.addAttribute("orderDetails", orderDetails);
        Order order = orderService.findById(id);
        String productName = "";
        for (OrderDetail orderDetail : orderDetails) {
            productName = petService.findCategoryNameByPetId(orderDetail.getProductId());
        }

        model.addAttribute("productName", productName);
        model.addAttribute("products", petService.findAllPet());
        model.addAttribute("order", order);

        model.addAttribute("router", "history-detail.jsp");
        return "index";
    }


}
