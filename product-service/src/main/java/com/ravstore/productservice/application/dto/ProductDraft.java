package com.ravstore.productservice.application.dto;

import com.ravstore.productservice.domain.Money;

public record ProductDraft(String name, Money price) {}
