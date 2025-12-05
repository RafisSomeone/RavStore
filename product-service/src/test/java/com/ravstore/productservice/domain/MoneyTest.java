package com.ravstore.productservice.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class MoneyTest {

  private static final Currency DEFAULT_CURRENCY = Currency.getInstance("USD");

  @Test
  void should_create_money_scale_to_2() {
    var amount = new BigDecimal("10");
    var expectedAmount = new BigDecimal("10.00");

    Money money = new Money(amount, DEFAULT_CURRENCY);

    assertEquals(expectedAmount, money.amount());
    assertEquals(DEFAULT_CURRENCY, money.currency());
  }

  @Test
  void should_not_create_if_amount_lower_than_zero() {
    var amount = new BigDecimal("-10");

    assertThrows(IllegalArgumentException.class, () -> new Money(amount, DEFAULT_CURRENCY));
  }
}
