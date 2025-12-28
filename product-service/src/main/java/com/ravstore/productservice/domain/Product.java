package com.ravstore.productservice.domain;

import java.util.UUID;

public record Product(UUID id, String name, Money price) {}
