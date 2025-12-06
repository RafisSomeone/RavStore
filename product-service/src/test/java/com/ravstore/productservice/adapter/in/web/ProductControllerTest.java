package com.ravstore.productservice.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravstore.productservice.configuration.ProductTestConfig;
import com.ravstore.productservice.mother.CreateProductRequestMother;
import com.ravstore.productservice.mother.ProductMother;
import com.ravstore.productservice.mother.UpdateProductRequestMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ProductTestConfig.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  private ResultActions createProduct(String json) throws Exception {
    return mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json));
  }

  private ResultActions updateProduct(String json, String id) throws Exception {
    return mockMvc.perform(
        put("/products/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));
  }

  static <T> T getBody(MvcResult result, Class<T> type, ObjectMapper objectMapper)
      throws IOException {
    return objectMapper.readValue(result.getResponse().getContentAsByteArray(), type);
  }

  @Test
  void should_create_product() throws Exception {
    String request = CreateProductRequestMother.validRequest();
    CreateProductRequest expected = objectMapper.readValue(request, CreateProductRequest.class);

    var result = createProduct(request).andExpect(status().isCreated()).andReturn();

    var body = getBody(result, ProductResponse.class, objectMapper);
    assertDoesNotThrow(() -> UUID.fromString(body.id().toString()));
    assertEquals(expected.name(), body.name());
    assertEquals(0, expected.price().amount().compareTo(new BigDecimal(body.amount())));
    assertEquals(expected.price().currency(), body.currency());
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource(
          "com.ravstore.productservice.mother.CreateProductRequestMother#invalidRequest")
  void should_throw_400_if_invalid_create_request(String request, String caseName) throws Exception {
    createProduct(request).andExpect(status().isBadRequest());
  }

  @Test
  void should_create_and_update_product() throws Exception {
    var createProductRequest = CreateProductRequestMother.validRequest();
    var updatedProductRequest = UpdateProductRequestMother.validRequest();
    var expected = objectMapper.readValue(updatedProductRequest, UpdateProductRequest.class);

    var result = createProduct(createProductRequest).andExpect(status().isCreated()).andReturn();
    var createBody = getBody(result, ProductResponse.class, objectMapper);

    var updateResult =
        updateProduct(updatedProductRequest, createBody.id().toString())
            .andExpect(status().isOk())
            .andReturn();
    var updateBody = getBody(updateResult, ProductResponse.class, objectMapper);

    assertEquals(expected.name(), updateBody.name());
    assertEquals(
        0, expected.price().amount().compareTo(new BigDecimal(updateBody.amount())));
    assertEquals(expected.price().currency(), updateBody.currency());
  }

  @Test
  void should_throw_404_if_does_not_exist() throws Exception {
    var request = UpdateProductRequestMother.validRequest();

    updateProduct(request, ProductMother.id().toString()).andExpect(status().isNotFound());
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("com.ravstore.productservice.mother.UpdateProductRequestMother#invalidRequest")
    void should_return_400_if_invalid_update_request(String request, String caseName) throws Exception {
      updateProduct(request, ProductMother.id().toString()).andExpect(status().isBadRequest());
  }
}
