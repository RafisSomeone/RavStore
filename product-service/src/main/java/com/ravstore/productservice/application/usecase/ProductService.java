package com.ravstore.productservice.application.usecase;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;

import java.util.UUID;

public class ProductService implements CreateProductUseCase {

    private final ProductStorage productStorage;

    public ProductService(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }
    @Override
    public Product createProduct(CreateProductCommand command) {
        var product = new Product(UUID.randomUUID(), command.name(), command.price());
        return productStorage.save(product);
    }
}
