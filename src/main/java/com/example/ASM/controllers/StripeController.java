package com.example.ASM.controllers;

import com.example.ASM.dto.StripeRequestDto;
import com.example.ASM.dto.StripeResponseDto;
import com.example.ASM.models.Order;
import com.example.ASM.models.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StripeController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @GetMapping("/test")
    public String home(Model model) {
        model.addAttribute("request", new StripeRequestDto());
        return "test";
    }

    @RequestMapping("/payment/stripe")
    public String showCard(Model model,
                           @RequestParam("amount") Long amount,
                           @RequestParam("email") String email,
                           @RequestParam("productName") String productName
                           ) {
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", amount);
        model.addAttribute("email", email);
        model.addAttribute("productName", productName);
        return "stripe-checkout";
    }

    @PostMapping("/create-payment-intent")
    @ResponseBody
    public ResponseEntity<StripeResponseDto> createPaymentIntent(@RequestBody @Valid StripeRequestDto request) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount() * 100L)
                .putMetadata("productName", request.getProductName())
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        StripeResponseDto responseDto = new StripeResponseDto(intent.getId(), intent.getClientSecret());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
