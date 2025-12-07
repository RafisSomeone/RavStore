package com.ravstore.productservice.application.dto.command;

import com.ravstore.productservice.domain.Money;

public record CreateProductCommand(String name, Money price) {}
