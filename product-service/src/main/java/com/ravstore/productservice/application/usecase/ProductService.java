package com.ravstore.productservice.application.usecase;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.application.exception.ProductNotFoundException;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.in.UpdateProductUseCase;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService implements CreateProductUseCase, UpdateProductUseCase {

  private final ProductStorage productStorage;
  private final BusinessMetrics metricsService;

  public ProductService(ProductStorage productStorage, BusinessMetrics metricsService) {
    this.productStorage = productStorage;
    this.metricsService = metricsService;
  }

  @Override
  public Product create(CreateProductCommand command) {
    log.info("Creating product name='{}'", command.name());

    try {
      var product = productStorage.create(new ProductDraft(command.name(), command.price()));

      log.info("Created product id={}, name='{}'", product.id(), product.name());
      metricsService.productCreateSuccessIncrement();

      return product;
    } catch (Exception e) {
      log.error("Product name='{}' creation failed", command.name());
      metricsService.productCreateFailIncrement();
      throw e;
    }
  }

  @Override
  public Product update(UpdateProductCommand command) {
    log.info("Updating product name='{}'", command.name());

    try {
      var updatedProduct =
          productStorage
              .update(new Product(command.id(), command.name(), command.price()))
              .orElseThrow(() -> new ProductNotFoundException(command.id()));

      log.info("Updated product id={}, name='{}'", updatedProduct.id(), updatedProduct.name());
      metricsService.productUpdateSuccessIncrement();

      return updatedProduct;
    } catch (Exception e) {
      log.error("Product name='{}' update failed", command.name());
      metricsService.productUpdateFailIncrement();
      throw e;
    }
  }
}
