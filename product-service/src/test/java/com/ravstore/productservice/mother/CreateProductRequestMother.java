package com.ravstore.productservice.mother;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.core.io.ClassPathResource;

public class CreateProductRequestMother {

  private static String jsonRequest(String jsonName) throws IOException {
    return new ClassPathResource("requests/create/" + jsonName)
        .getContentAsString(StandardCharsets.UTF_8);
  }

  public static String validRequest() throws IOException {
    return jsonRequest("product.json");
  }

  public static Stream<Arguments> invalidRequest() throws IOException {
    return Stream.of(
        Arguments.of(jsonRequest("blank-name-product.json"), "blank name"),
        Arguments.of(jsonRequest("no-name-product.json"), "no name"),
        Arguments.of(jsonRequest("no-price-product.json"), "no price"),
        Arguments.of(jsonRequest("no-currency-product.json"), "no currency"),
        Arguments.of(jsonRequest("invalid-currency-product.json"), "invalid currency"),
        Arguments.of(jsonRequest("no-amount-product.json"), "no amount"),
        Arguments.of(jsonRequest("negative-price-product.json"), "negative price"));
  }
}
