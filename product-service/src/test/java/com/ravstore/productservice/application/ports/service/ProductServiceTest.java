package com.ravstore.productservice.application.ports.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.usecase.ProductService;
import com.ravstore.productservice.domain.Product;
import com.ravstore.productservice.fixtures.ProductFixtures;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock private ProductStorage mockStorage;

  @Mock private BusinessMetrics mockMetrics;

  @Captor private ArgumentCaptor<ProductDraft> captor;

  @InjectMocks private ProductService productService;

  @Test
  void should_create_a_product() {
    var createProductCommand =
        new CreateProductCommand(ProductFixtures.name(), ProductFixtures.money10USD());
    when(mockStorage.create(any(ProductDraft.class))).thenReturn(ProductFixtures.product());

    productService.create(createProductCommand);

    verify(mockStorage).create(captor.capture());
    var created = captor.getValue();

    assertEquals(ProductFixtures.name(), created.name());
    assertEquals(ProductFixtures.money10USD(), created.price());
  }

  @Test
  void should_update_a_product() {
    var updateProductCommand = ProductFixtures.updateProductCommand();
    when(mockStorage.update(any(Product.class))).thenReturn(Optional.of(ProductFixtures.product()));

    productService.update(updateProductCommand);

    ArgumentCaptor<Product> captor = ArgumentCaptor.captor();
    verify(mockStorage).update(captor.capture());
    var updated = captor.getValue();

    assertEquals(ProductFixtures.product(), updated);
  }

  @Test
  void should_throw_exception_if_not_exist() {
    var updateProductCommand = ProductFixtures.updateProductCommand();
    when(mockStorage.update(any(Product.class))).thenReturn(Optional.empty());

    var response = productService.update(updateProductCommand);
    assertEquals(new NotFound(), response.getLeft());
  }
}
