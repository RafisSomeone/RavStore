package com.ravstore.productservice.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
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

    then(money.amount()).isEqualTo(expectedAmount);
    then(money.currency()).isEqualTo(DEFAULT_CURRENCY);
  }

  @Test
  void should_not_create_if_amount_lower_than_zero() {
    var amount = new BigDecimal("-10");

    thenThrownBy(() -> new Money(amount, DEFAULT_CURRENCY))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
