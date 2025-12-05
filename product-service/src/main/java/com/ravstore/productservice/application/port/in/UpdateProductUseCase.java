package com.ravstore.productservice.application.port.in;

import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.domain.Product;

public interface UpdateProductUseCase {
  Product update(UpdateProductCommand command);
}
