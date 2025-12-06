package com.ravstore.productservice.configuration;

import com.ravstore.productservice.adapter.out.InMemoryBusinessMetrics;
import com.ravstore.productservice.adapter.out.InMemoryProductStorage;
import com.ravstore.productservice.adapter.out.InMemorySystemMetrics;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.in.UpdateProductUseCase;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.port.out.SystemMetrics;
import com.ravstore.productservice.application.usecase.ProductService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProductTestConfig {

  @Bean
  BusinessMetrics businessMetrics() {
    return new InMemoryBusinessMetrics();
  }

  @Bean
  SystemMetrics systemMetrics() {
    return new InMemorySystemMetrics();
  }

  @Bean
  ProductStorage productStorage(BusinessMetrics metricsService) {
    return new InMemoryProductStorage();
  }

  @Bean
  CreateProductUseCase createProductUseCase(
      ProductStorage productStorage, BusinessMetrics metricsService) {
    return new ProductService(productStorage, metricsService);
  }

  @Bean
  UpdateProductUseCase updateProductUseCase(
      ProductStorage productStorage, BusinessMetrics metricsService) {
    return new ProductService(productStorage, metricsService);
  }
}
