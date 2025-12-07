package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.port.out.BusinessMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class MeterBusinessMetrics implements BusinessMetrics {

  private final Counter productCreateSuccess;
  private final Counter productCreateFail;
  private final Counter productUpdateSuccess;
  private final Counter productUpdateFail;
  private final Counter productGetSuccess;
  private final Counter productGetFail;

  public MeterBusinessMetrics(MeterRegistry registry) {
    productCreateSuccess =
        Counter.builder("ravstore.product-service.create.success.count")
            .description("Number of products created")
            .register(registry);

    productCreateFail =
        Counter.builder("ravstore.product-service.create.fail.count")
            .description("Number of failed product creations")
            .register(registry);

    productUpdateSuccess =
        Counter.builder("rav.store.product-service.update.fail.count")
            .description("Number of product updates")
            .register(registry);

    productUpdateFail =
        Counter.builder("rav.store.product-service.update.fail.count")
            .description("Number of failed product updates")
            .register(registry);

    productGetSuccess =
        Counter.builder("rav.store.product-service.get.success.count")
            .description("Number of fetched products")
            .register(registry);
    productGetFail =
        Counter.builder("rav.store.product-service.get.fail.count")
            .description("Number of failed product fetches")
            .register(registry);
  }

  @Override
  public void productCreateSuccessIncrement() {
    productCreateSuccess.increment();
  }

  @Override
  public void productCreateFailIncrement() {
    productCreateFail.increment();
  }

  @Override
  public void productUpdateSuccessIncrement() {
    productUpdateSuccess.increment();
  }

  @Override
  public void productUpdateFailIncrement() {
    productUpdateFail.increment();
  }

  @Override
  public void productGetSuccessIncrement() {
    productGetSuccess.increment();
  }

  @Override
  public void productGetFailIncrement() {
    productGetFail.increment();
  }
}
