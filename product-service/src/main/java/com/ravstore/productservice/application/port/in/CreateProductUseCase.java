package com.ravstore.productservice.application.port.in;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.domain.Product;

public interface CreateProductUseCase {
  Product create(CreateProductCommand command);
}
