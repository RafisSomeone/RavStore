package com.ravstore.productservice.application.usecase;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravstore.productservice.application.error.NotFound;
import com.ravstore.productservice.application.port.out.BusinessMetrics;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.mother.domain.ProductMother;
import com.ravstore.productservice.mother.query.ProductQueryMother;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

  @Mock BusinessMetrics metrics;

  @Mock ProductStorage storage;

  @InjectMocks ProductQueryService queryService;

  @Test
  void should_return_product() {
    var id = ProductMother.id();
    var query = ProductQueryMother.query();
    var product = ProductMother.product();
    when(storage.get(id)).thenReturn(Optional.of(product));

    var result = queryService.get(query);

    then(result.isRight()).isTrue();
    then(result.get()).isEqualTo(product);
    verify(metrics).productGetSuccessIncrement();
  }

  @Test
  void should_return_not_found_if_empty() {
    var id = ProductMother.id();
    var query = ProductQueryMother.query();
    when(storage.get(id)).thenReturn(Optional.empty());

    var result = queryService.get(query);

    then(result.isLeft()).isTrue();
    then(result.getLeft()).isInstanceOf(NotFound.class);
  }
}
