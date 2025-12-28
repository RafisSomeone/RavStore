package com.ravstore.productservice.adapter.in.web;

import static org.assertj.core.api.BDDAssertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravstore.productservice.configuration.ProductTestConfig;
import com.ravstore.productservice.mother.domain.ProductMother;
import com.ravstore.productservice.mother.request.CreateProductRequestMother;
import com.ravstore.productservice.mother.request.UpdateProductRequestMother;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
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
        put("/products/" + id).contentType(MediaType.APPLICATION_JSON).content(json));
  }

  private ResultActions getProduct(String id) throws Exception {
    return mockMvc.perform(get("/products/" + id).contentType(MediaType.APPLICATION_JSON));
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

    thenNoException().isThrownBy(() -> UUID.fromString(body.id().toString()));
    then(body.name()).isEqualTo(expected.name());
    then(expected.price().amount()).isEqualByComparingTo(new BigDecimal(body.amount()));
    then(body.currency()).isEqualTo(expected.price().currency());
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource(
      "com.ravstore.productservice.mother.request.CreateProductRequestMother#invalidRequest")
  void should_throw_400_if_invalid_create_request(String request, String caseName)
      throws Exception {
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

    then(updateBody.name()).isEqualTo(expected.name());
    then(expected.price().amount()).isEqualByComparingTo(new BigDecimal(updateBody.amount()));
    then(updateBody.currency()).isEqualTo(expected.price().currency());
  }

  @Test
  void should_throw_404_if_product_to_update_does_not_exist() throws Exception {
    var request = UpdateProductRequestMother.validRequest();

    updateProduct(request, ProductMother.id().toString()).andExpect(status().isNotFound());
  }

  @ParameterizedTest(name = "{2}")
  @MethodSource(
      "com.ravstore.productservice.mother.request.UpdateProductRequestMother#invalidRequest")
  void should_return_400_if_invalid_update_request(String request, String id, String caseName)
      throws Exception {
    updateProduct(request, id).andExpect(status().isBadRequest());
  }

  @Test
  void should_return_project_if_exist() throws Exception {
    String request = CreateProductRequestMother.validRequest();
    CreateProductRequest expected = objectMapper.readValue(request, CreateProductRequest.class);

    var result = createProduct(request).andExpect(status().isCreated()).andReturn();
    var body = getBody(result, ProductResponse.class, objectMapper);

    var response = getProduct(body.id().toString()).andExpect(status().isOk()).andReturn();

    var product = getBody(response, ProductResponse.class, objectMapper);

    then(product.name()).isEqualTo(expected.name());
    then(product.currency()).isEqualTo(expected.price().currency());
    then(new BigDecimal(product.amount())).isEqualByComparingTo(expected.price().amount());
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource(
      "com.ravstore.productservice.mother.request.GetProductRequestMother#invalidRequests")
  void should_return_400_if_incorrect_get_request(String id, String caseName) throws Exception {
    getProduct(id).andExpect(status().isBadRequest());
  }

  @Test
  void get_should_return_404_if_product_not_exist() throws Exception {
    getProduct(ProductMother.id().toString()).andExpect(status().isNotFound());
  }
}
