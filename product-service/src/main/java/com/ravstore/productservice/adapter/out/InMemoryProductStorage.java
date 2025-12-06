package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.port.out.MetricsService;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InMemoryProductStorage implements ProductStorage {

  private final MetricsService metricsService;

  public InMemoryProductStorage(MetricsService metricsService) {
    this.metricsService = metricsService;
  }

  private final Map<UUID, Product> database = new ConcurrentHashMap<>();

  @Override
  public Product create(ProductDraft draft) {
    try {

      var product = new Product(UUID.randomUUID(), draft.name(), draft.price());
      database.put(product.id(), product);
      return product;

    } catch (Exception e) { // Let's assume this is a normal database save;
      log.error("Database call failed, creating product name='{}`", draft.name());
      metricsService.databaseCallFailIncrement();
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Product> update(Product product) {
    try {
      database.put(product.id(), product);
      return Optional.of(product);
    } catch (Exception e) {
      log.error(
          "Database call failed, saving product id={}, name='{}`", product.id(), product.name());
      metricsService.databaseCallFailIncrement();
      throw new RuntimeException(e);
    }
  }
}
