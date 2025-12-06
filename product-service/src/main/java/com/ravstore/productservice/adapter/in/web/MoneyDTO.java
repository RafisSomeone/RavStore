package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MoneyDTO(
    @NotNull @DecimalMin(value = "0") BigDecimal amount, @NotNull @ValidCurrency String currency) {}
