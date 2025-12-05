package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequestBody(
        @NotBlank String name,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal amount,

        @NotBlank
        @ValidCurrency
        String currency
) {
}
