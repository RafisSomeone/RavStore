package com.ravstore.productservice.mother.request;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class GetProductRequestMother {
  public static Stream<Arguments> invalidRequests() {
    return Stream.of(Arguments.of(null, "no id"), Arguments.of("notUUID", "not UUID"));
  }
}
