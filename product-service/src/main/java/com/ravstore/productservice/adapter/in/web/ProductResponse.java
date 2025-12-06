package com.ravstore.productservice.adapter.in.web;

import com.ravstore.productservice.domain.Product;
import java.util.UUID;

record ProductResponse(UUID id, String name, String amount, String currency) {

  static ProductResponse from(Product product) {
    return new ProductResponse(
        product.id(),
        product.name(),
        product.price().amount().toPlainString(),
        product.price().currency().getCurrencyCode());
  }
}
