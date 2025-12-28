package com.ravstore.productservice.config;

import com.ravstore.productservice.adapter.out.JooqProductStorage;
import com.ravstore.productservice.adapter.out.MeterBusinessMetrics;
import com.ravstore.productservice.adapter.out.MeterSystemMetrics;
import com.ravstore.productservice.application.port.in.ProductHandler;
import com.ravstore.productservice.application.port.in.ProductQueryHandler;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.port.out.SystemMetrics;
import com.ravstore.productservice.application.usecase.ProductQueryService;
import com.ravstore.productservice.application.usecase.ProductService;
import io.micrometer.core.instrument.MeterRegistry;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductConfig {

  @Bean
  ProductHandler productHandler(ProductStorage productStorage, BusinessMetrics metricsService) {
    return new ProductService(productStorage, metricsService);
  }

  @Bean
  ProductStorage productStorage(DSLContext dsl) {
    return new JooqProductStorage(dsl);
  }

  @Bean
  SystemMetrics systemMetrics(MeterRegistry meterRegistry) {
    return new MeterSystemMetrics(meterRegistry);
  }

  @Bean
  BusinessMetrics businessMetrics(MeterRegistry meterRegistry) {
    return new MeterBusinessMetrics(meterRegistry);
  }

  @Bean
  ProductQueryHandler productQueryHandler(ProductStorage storage, BusinessMetrics metrics) {
    return new ProductQueryService(storage, metrics);
  }
}
