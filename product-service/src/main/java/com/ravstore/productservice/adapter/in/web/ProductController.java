package com.ravstore.productservice.adapter.in.web;

import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.domain.Money;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Currency;

@RestController
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@Validated @RequestBody CreateProductRequestBody body) {
        var command = new CreateProductCommand(body.name(), new Money(body.amount(), Currency.getInstance(body.currency())));

        var product = createProductUseCase.createProduct(command);
        var response = ProductResponse.from(product);
        var location = URI.create("/products/" + product.id());

        return ResponseEntity.created(location).body(response);
    }
}
