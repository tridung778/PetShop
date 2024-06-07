package com.example.ASM.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StripeRequestDto {

    private Long amount;

    private String email;

    private String productName;
}
