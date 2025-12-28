package com.ravstore.productservice.mother.request;

import com.ravstore.productservice.mother.domain.ProductMother;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.core.io.ClassPathResource;

public class UpdateProductRequestMother {
  private static String jsonRequest(String jsonName) throws IOException {
    return new ClassPathResource("requests/update/" + jsonName)
        .getContentAsString(StandardCharsets.UTF_8);
  }

  public static String validRequest() throws IOException {
    return jsonRequest("product.json");
  }

  public static Stream<Arguments> invalidRequest() throws IOException {
    var id = ProductMother.id().toString();
    return Stream.of(
        Arguments.of(jsonRequest("blank-name-product.json"), id, "blank name"),
        Arguments.of(jsonRequest("no-name-product.json"), id, "no name"),
        Arguments.of(jsonRequest("no-price-product.json"), id, "no price"),
        Arguments.of(jsonRequest("no-currency-product.json"), id, "no currency"),
        Arguments.of(jsonRequest("invalid-currency-product.json"), id, "invalid currency"),
        Arguments.of(jsonRequest("no-amount-product.json"), id, "no amount"),
        Arguments.of(jsonRequest("negative-price-product.json"), id, "negative price"),
        Arguments.of(jsonRequest("product.json"), "notUUID", "Id is not UUID"));
  }
}
