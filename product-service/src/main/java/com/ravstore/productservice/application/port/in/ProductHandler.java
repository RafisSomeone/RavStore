package com.ravstore.productservice.application.port.in;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.domain.Product;

public interface ProductHandler {
  Product create(CreateProductCommand command);

  Product update(UpdateProductCommand command);
}
