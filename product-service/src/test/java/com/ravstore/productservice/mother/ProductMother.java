package com.ravstore.productservice.mother;

import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.domain.Money;
import com.ravstore.productservice.domain.Product;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class ProductMother {

  public static final String CURRENCY = "USD";

  public static UUID id() {
    return UUID.fromString("170B11E6-11D6-4EC2-AE31-A731291B7B20");
  }

  public static String name() {
    return "productName";
  }

  public static Money money10USD() {
    return new Money(new BigDecimal("10"), Currency.getInstance(CURRENCY));
  }

  public static Product product() {
    return new Product(id(), name(), money10USD());
  }

  public static UpdateProductCommand updateProductCommand() {
    return new UpdateProductCommand(id(), name(), money10USD());
  }
}
