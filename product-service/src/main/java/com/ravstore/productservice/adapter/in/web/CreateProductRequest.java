package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest(@NotBlank String name, @Valid MoneyDTO price) {}
