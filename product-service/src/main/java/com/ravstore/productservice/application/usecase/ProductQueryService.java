package com.ravstore.productservice.application.usecase;

import com.ravstore.productservice.application.dto.query.GetProductQuery;
import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.application.port.in.ProductQueryHandler;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductQueryService implements ProductQueryHandler {

  private final ProductStorage storage;
  private final BusinessMetrics metrics;

  public ProductQueryService(ProductStorage storage, BusinessMetrics metrics) {
    this.storage = storage;
    this.metrics = metrics;
  }

  @Override
  public Either<NotFound, Product> get(GetProductQuery query) {
    try {

      return storage
          .get(query.id())
          .<Either<NotFound, Product>>map(
              product -> {
                log.info("Successfully fetched product id={}", product.id());
                metrics.productGetSuccessIncrement();
                return Either.right(product);
              })
          .orElseGet(() -> Either.left(new NotFound()));

    } catch (Exception e) {
      log.error("Get product id={} failed", query.id());
      metrics.productGetFailIncrement();
      throw e;
    }
  }
}
