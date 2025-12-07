package com.ravstore.productservice.adapter.in.web;

import com.ravstore.productservice.application.dto.command.CreateProductCommand;
import com.ravstore.productservice.application.dto.command.UpdateProductCommand;
import com.ravstore.productservice.application.dto.query.GetProductQuery;
import com.ravstore.productservice.application.port.in.ProductHandler;
import com.ravstore.productservice.application.port.in.ProductQueryHandler;
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

  private final ProductHandler productHandler;
  private final ProductQueryHandler queryHandler;

  ProductController(ProductHandler productHandler, ProductQueryHandler queryHandler) {
    this.productHandler = productHandler;
    this.queryHandler = queryHandler;
  }

  @PostMapping()
  ResponseEntity<ProductResponse> createProduct(@Validated @RequestBody CreateProductRequest body) {
    var command =
        new CreateProductCommand(
            body.name(),
            new Money(body.price().amount(), Currency.getInstance(body.price().currency())));

    var product = productHandler.create(command);
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
    return productHandler
        .update(command)
        .fold(
            error -> ResponseEntity.notFound().build(),
            product -> ResponseEntity.ok(ProductResponse.from(product)));
  }

  @GetMapping("/{id}")
  ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
    var query = new GetProductQuery(id);
    return queryHandler
        .get(query)
        .fold(
            error -> ResponseEntity.notFound().build(),
            product -> ResponseEntity.ok(ProductResponse.from(product)));
  }
}
