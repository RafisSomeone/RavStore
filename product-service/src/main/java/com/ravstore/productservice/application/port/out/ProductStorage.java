package com.ravstore.productservice.application.port.out;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.domain.Product;
import java.util.Optional;

public interface ProductStorage {
  Product create(ProductDraft productDraft);

  Optional<Product> update(Product product);
}
