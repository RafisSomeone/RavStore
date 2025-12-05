package com.ravstore.productservice.application.port.out;

import com.ravstore.productservice.domain.Product;

public interface ProductStorage {
    Product save(Product product);
}
