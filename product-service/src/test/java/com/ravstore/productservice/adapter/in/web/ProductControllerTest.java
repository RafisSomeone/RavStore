package com.ravstore.productservice.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravstore.productservice.configuration.ProductTestConfig;
import com.ravstore.productservice.fixtures.ProductFixtures;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ProductController.class)
@Import(ProductTestConfig.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  private ResultActions createProduct(CreateProductRequest request) throws Exception {
    return mockMvc.perform(
        post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
  }

  private ResultActions updateProduct(UpdateProductRequest request, String id) throws Exception {
    return mockMvc.perform(
        put("/products/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
  }

  static Stream<Arguments> invalidRequests() {
    return Stream.of(
        Arguments.of(new CreateProductRequest("", ProductFixtures.moneyDTO10USD()), "blank name"),
        Arguments.of(new CreateProductRequest(null, ProductFixtures.moneyDTO10USD()), "no name"),
        Arguments.of(
            new CreateProductRequest(
                ProductFixtures.name(), new MoneyDTO(new BigDecimal("-10"), "USD")),
            "negative price"));
  }

  static <T> T getBody(
    MvcResult result, Class<T> type, ObjectMapper objectMapper) throws IOException {
      return objectMapper.readValue(result.getResponse().getContentAsByteArray(), type);
    }

  @Test
  void should_create_product() throws Exception {
    var request = ProductFixtures.createProductRequest();

    var result = createProduct(request).andExpect(status().isCreated()).andReturn();

    var body = getBody(result, ProductResponse.class, objectMapper);
    assertDoesNotThrow(() -> UUID.fromString(body.id().toString()));
    assertEquals(request.name(), body.name());
    assertEquals(request.price().amount().toString(), body.amount());
    assertEquals(request.price().currency(), body.currency());
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("invalidRequests")
  void should_throw_400_if_invalid_request(CreateProductRequest request, String caseName)
      throws Exception {

    createProduct(request).andExpect(status().isBadRequest());
  }

  @Test
  void should_create_and_update_product() throws Exception {
    var createProductRequest = ProductFixtures.createProductRequest();
    var updatedProductRequest = ProductFixtures.updateProductRequest();

    var result = createProduct(createProductRequest).andExpect(status().isCreated()).andReturn();
    var body = result.getResponse().getContentAsString();
    var id = objectMapper.readTree(body).get("id").asText();

    updateProduct(updatedProductRequest, id).andExpect(status().isOk());
  }

  @Test
  void should_throw_404_if_does_not_exist() throws Exception {
    var request = ProductFixtures.updateProductRequest();

    updateProduct(request, ProductFixtures.id().toString()).andExpect(status().isNotFound());
  }
}
