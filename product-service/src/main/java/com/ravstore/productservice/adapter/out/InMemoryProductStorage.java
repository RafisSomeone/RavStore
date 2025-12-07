package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InMemoryProductStorage implements ProductStorage {

  public InMemoryProductStorage() {}

  private final Map<UUID, Product> database = new ConcurrentHashMap<>();

  @Override
  public Product create(ProductDraft draft) {
    var product = new Product(UUID.randomUUID(), draft.name(), draft.price());
    database.put(product.id(), product);
    return product;
  }

  @Override
  public Optional<Product> update(Product product) {
    if (!database.containsKey(product.id())) return Optional.empty();

    database.put(product.id(), product);
    return Optional.of(product);
  }

  @Override
  public Optional<Product> get(UUID id) {
    var product = database.get(id);
    return Optional.ofNullable(product);
  }
}
