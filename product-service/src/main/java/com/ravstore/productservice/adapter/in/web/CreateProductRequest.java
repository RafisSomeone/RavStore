package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

record CreateProductRequest(@NotBlank String name, @Valid MoneyDTO price) {}
