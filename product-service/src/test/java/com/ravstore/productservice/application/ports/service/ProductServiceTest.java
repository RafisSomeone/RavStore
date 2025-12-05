package com.ravstore.productservice.application.ports.service;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.usecase.ProductService;
import com.ravstore.productservice.domain.Money;
import com.ravstore.productservice.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductStorage mockStorage;

    @Captor
    private ArgumentCaptor<Product> captor;

    @InjectMocks
    private ProductService productService;

    @Test
    void should_create_a_product() {
        var productName = "mockProduct";
        var price = new Money(new BigDecimal("1000.99"), Currency.getInstance("USD"));
        var createProductCommand = new CreateProductCommand(productName, price);

        productService.createProduct(createProductCommand);

        verify(mockStorage).save(captor.capture());
        Product saved = captor.getValue();

        assertEquals(productName, saved.name());
        assertEquals(price, saved.price());
        assertNotNull(saved.id());
    }

}