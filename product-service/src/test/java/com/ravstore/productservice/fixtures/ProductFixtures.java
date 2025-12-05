package com.ravstore.productservice.fixtures;

import com.ravstore.productservice.adapter.in.web.CreateProductRequest;
import com.ravstore.productservice.adapter.in.web.MoneyDTO;
import com.ravstore.productservice.adapter.in.web.UpdateProductRequest;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.domain.Money;
import com.ravstore.productservice.domain.Product;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class ProductFixtures {
  public static UUID id() {
    return UUID.fromString("170B11E6-11D6-4EC2-AE31-A731291B7B20");
  }

  public static String name() {
    return "productName";
  }

  public static Money money10USD() {
    return new Money(new BigDecimal("10"), Currency.getInstance("USD"));
  }

  public static MoneyDTO moneyDTO10USD() {
    return new MoneyDTO(new BigDecimal("10"), "USD");
  }

  public static Product product() {
    return new Product(id(), name(), money10USD());
  }

  public static CreateProductRequest createProductRequest() {
    return new CreateProductRequest(
        name(), new MoneyDTO(money10USD().amount(), money10USD().currency().toString()));
  }

  public static UpdateProductRequest updateProductRequest() {
    return new UpdateProductRequest(name(), moneyDTO10USD());
  }

  public static UpdateProductCommand updateProductCommand() {
    return new UpdateProductCommand(id(), name(), money10USD());
  }
}
