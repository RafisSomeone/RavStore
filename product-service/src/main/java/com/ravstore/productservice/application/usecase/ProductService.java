package com.ravstore.productservice.application.usecase;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.dto.command.CreateProductCommand;
import com.ravstore.productservice.application.dto.command.UpdateProductCommand;
import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.application.error.UpdateProductError;
import com.ravstore.productservice.application.port.in.ProductHandler;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService implements ProductHandler {

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
  public Either<UpdateProductError, Product> update(UpdateProductCommand command) {
    log.info("Updating product name='{}'", command.name());

    try {
      return productStorage
          .update(new Product(command.id(), command.name(), command.price()))
          .<Either<UpdateProductError, Product>>map(
              product -> {
                log.info("Updated product id={}, name='{}'", product.id(), product.name());
                metricsService.productUpdateSuccessIncrement();
                return Either.right(product);
              })
          .orElseGet(() -> Either.left(new NotFound()));

    } catch (Exception e) {
      log.error("Product name='{}' update failed", command.name());
      metricsService.productUpdateFailIncrement();
      throw e;
    }
  }
}
