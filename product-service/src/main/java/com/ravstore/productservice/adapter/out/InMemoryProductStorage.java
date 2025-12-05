package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Product;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductStorage implements ProductStorage {
    private final Map<UUID, Product> database = new ConcurrentHashMap<>();


    @Override
    public Product save(Product product) {
        database.put(product.id(), product);
        return product;
    }

}
