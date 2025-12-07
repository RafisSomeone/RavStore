package com.ravstore.productservice.application.ports.service;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.*;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.dto.command.CreateProductCommand;
import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.application.usecase.ProductService;
import com.ravstore.productservice.domain.Product;
import com.ravstore.productservice.mother.domain.ProductMother;
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
        new CreateProductCommand(ProductMother.name(), ProductMother.money10USD());
    when(mockStorage.create(any(ProductDraft.class))).thenReturn(ProductMother.product());

    productService.create(createProductCommand);

    verify(mockStorage).create(captor.capture());
    verify(mockMetrics).productCreateSuccessIncrement();
    var created = captor.getValue();

    then(created.name()).isEqualTo(ProductMother.name());
    then(created.price()).isEqualTo(ProductMother.money10USD());
  }

  @Test
  void should_update_a_product() {
    var updateProductCommand = ProductMother.updateProductCommand();
    when(mockStorage.update(any(Product.class))).thenReturn(Optional.of(ProductMother.product()));

    productService.update(updateProductCommand);
    verify(mockMetrics).productUpdateSuccessIncrement();

    ArgumentCaptor<Product> captor = ArgumentCaptor.captor();
    verify(mockStorage).update(captor.capture());
    var updated = captor.getValue();

    then(updated).isEqualTo(ProductMother.product());
  }

  @Test
  void should_throw_exception_if_not_exist() {
    var updateProductCommand = ProductMother.updateProductCommand();
    when(mockStorage.update(any(Product.class))).thenReturn(Optional.empty());

    var response = productService.update(updateProductCommand);

    then(response.getLeft()).isEqualTo(new NotFound());
  }
}
