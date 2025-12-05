package com.ravstore.productservice.adapter.in.web;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Currency;

public class CurrencyCodeValidator implements ConstraintValidator<ValidCurrency, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return true;
    }

    try {
      Currency.getInstance(value);
      return true;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }
}
