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
public class ProductConfig {

  @Bean
  public CreateProductUseCase createProductUseCase(
      ProductStorage productStorage, MetricsService metricsService) {
    return new ProductService(productStorage, metricsService);
  }

  @Bean
  public ProductStorage productStorage(MetricsService metricsService) {
    return new InMemoryProductStorage(metricsService);
  }

  @Bean
  public MetricsService metricsService(MeterRegistry meterRegistry) {
    return new MicrometerMetricsService(meterRegistry);
  }

  @Bean
  public UpdateProductUseCase updateProductUseCase(
      ProductStorage productStorage, MetricsService metricsService) {
    return new ProductService(productStorage, metricsService);
  }
}
