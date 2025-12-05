package com.ravstore.productservice.config;

import com.ravstore.productservice.adapter.out.InMemoryProductStorage;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.usecase.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public CreateProductUseCase createProductUseCase(ProductStorage productStorage) {
        return new ProductService(productStorage);
    }

    @Bean
    public ProductStorage productStorage() {
        return new InMemoryProductStorage();
    }
}
