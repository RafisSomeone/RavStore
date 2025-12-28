package com.ravstore.productservice.application.dto.command;

import com.ravstore.productservice.domain.Money;
import java.util.UUID;

public record UpdateProductCommand(UUID id, String name, Money price) {}
