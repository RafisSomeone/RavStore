package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(@NotBlank String name, @NotNull @Valid MoneyDTO price) {}
