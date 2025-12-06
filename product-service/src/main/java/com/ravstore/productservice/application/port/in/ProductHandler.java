package com.ravstore.productservice.application.port.in;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.application.error.UpdateProductError;
import com.ravstore.productservice.domain.Product;
import io.vavr.control.Either;

public interface ProductHandler {
  Product create(CreateProductCommand command);

  Either<UpdateProductError, Product> update(UpdateProductCommand command);
}
