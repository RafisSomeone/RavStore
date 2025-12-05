package com.ravstore.productservice.adapter.in.web;

import com.ravstore.productservice.domain.Product;

import java.util.UUID;

public record ProductResponse(UUID id, String name, String amount, String currency) {

    public static ProductResponse from(Product product) {
       return new ProductResponse(product.id(), product.name(), product.price().amount().toPlainString(), product.price().currency().getCurrencyCode());
    }
}
