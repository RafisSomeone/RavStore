package com.ravstore.productservice.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravstore.productservice.application.dto.CreateProductCommand;
import com.ravstore.productservice.application.dto.UpdateProductCommand;
import com.ravstore.productservice.application.exception.ProductNotFoundException;
import com.ravstore.productservice.application.port.in.CreateProductUseCase;
import com.ravstore.productservice.application.port.in.UpdateProductUseCase;
import com.ravstore.productservice.fixtures.ProductFixtures;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @MockitoBean private CreateProductUseCase createProductUseCase;

  @MockitoBean private UpdateProductUseCase updateProductUseCase;

  private ResultActions createProduct(CreateProductRequest request) throws Exception {
    return mockMvc.perform(
        post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
  }

  private ResultActions updateProduct(UpdateProductRequest request) throws Exception {
    return mockMvc.perform(
        put("/products/" + ProductFixtures.id())
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

  @Test
  void should_create_product() throws Exception {
    var request = ProductFixtures.createProductRequest();
    when(createProductUseCase.create(any(CreateProductCommand.class)))
        .thenReturn(ProductFixtures.product());

    createProduct(request)
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/products/" + ProductFixtures.id()));
  }

  @ParameterizedTest(name = "{1}")
  @MethodSource("invalidRequests")
  void should_throw_400_if_invalid_request(CreateProductRequest request, String caseName)
      throws Exception {
    verify(createProductUseCase, never()).create(any(CreateProductCommand.class));

    createProduct(request).andExpect(status().isBadRequest());
  }

  @Test
  void should_update_product() throws Exception {
    var request = ProductFixtures.updateProductRequest();
    when(updateProductUseCase.update(any(UpdateProductCommand.class)))
        .thenReturn(ProductFixtures.product());

    updateProduct(request).andExpect(status().isOk());
  }

  @Test
  void should_throw_404_if_does_not_exist() throws Exception {
    var request = ProductFixtures.updateProductRequest();
    when(updateProductUseCase.update(any(UpdateProductCommand.class)))
        .thenThrow(new ProductNotFoundException(ProductFixtures.id()));

    updateProduct(request).andExpect(status().isNotFound());
  }
}
