package com.ravstore.productservice.application.port.in;

import com.ravstore.productservice.application.dto.query.GetProductQuery;
import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.domain.Product;
import io.vavr.control.Either;

public interface ProductQueryHandler {
  Either<NotFound, Product> get(GetProductQuery query);
}
