package com.ravstore.productservice.adapter.in.web;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CurrencyCodeValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidCurrency {
  String message() default "Invalid currency code";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
