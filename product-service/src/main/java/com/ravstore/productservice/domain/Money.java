package com.ravstore.productservice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

  public Money {
    Objects.requireNonNull(amount, "amount must not be null");
    Objects.requireNonNull(currency, "currency must not be null");

    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("amount must be non-negative");

    amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
  }

  public long toMinor() {
    return amount.movePointRight(currency.getDefaultFractionDigits()).longValueExact();
  }

  public static Money fromMinor(long minor, Currency currency) {
    return new Money(BigDecimal.valueOf(minor, currency.getDefaultFractionDigits()), currency);
  }
}
