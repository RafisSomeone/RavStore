package com.ravstore.productservice.adapter.in.web;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.application.port.in.ProductHandler;
import com.ravstore.productservice.domain.Money;
import java.net.URI;
import java.util.Currency;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
class ProductController {

  private final ProductHandler createProductUseCase;
  private final ProductHandler productHandler;

  ProductController(ProductHandler createProductUseCase, ProductHandler productHandler) {
    this.createProductUseCase = createProductUseCase;
    this.productHandler = productHandler;
  }

  @PostMapping()
  ResponseEntity<ProductResponse> createProduct(@Validated @RequestBody CreateProductRequest body) {
    var command =
        new CreateProductCommand(
            body.name(),
            new Money(body.price().amount(), Currency.getInstance(body.price().currency())));

    var product = createProductUseCase.create(command);
    var response = ProductResponse.from(product);
    var location = URI.create("/products/" + product.id());

    return ResponseEntity.created(location).body(response);
  }

  @PutMapping("/{id}")
  ResponseEntity<ProductResponse> updateProduct(
      @PathVariable UUID id, @Validated @RequestBody UpdateProductRequest body) {
    var command =
        new UpdateProductCommand(
            id,
            body.name(),
            new Money(body.price().amount(), Currency.getInstance(body.price().currency())));
    var product = productHandler.update(command);

    return ResponseEntity.ok(ProductResponse.from(product));
  }
}
