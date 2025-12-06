package com.ravstore.productservice.config;

import com.ravstore.productservice.adapter.out.InMemoryProductStorage;
import com.ravstore.productservice.adapter.out.MicrometerMetricsService;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.in.UpdateProductUseCase;
import com.ravstore.productservice.application.port.out.MetricsService;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.usecase.ProductService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductConfig {

  @Bean
  CreateProductUseCase createProductUseCase(
      ProductStorage productStorage, MetricsService metricsService) {
    return new ProductService(productStorage, metricsService);
  }

  @Bean
  ProductStorage productStorage(MetricsService metricsService) {
    return new InMemoryProductStorage(metricsService);
  }

  @Bean
  MetricsService metricsService(MeterRegistry meterRegistry) {
    return new MicrometerMetricsService(meterRegistry);
  }

  @Bean
  UpdateProductUseCase updateProductUseCase(
      ProductStorage productStorage, MetricsService metricsService) {
    return new ProductService(productStorage, metricsService);
  }
}
