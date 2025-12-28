package com.ravstore.productservice.mother.query;

import com.ravstore.productservice.application.dto.query.GetProductQuery;
import com.ravstore.productservice.mother.domain.ProductMother;

public class ProductQueryMother {

  public static GetProductQuery query() {
    return new GetProductQuery(ProductMother.id());
  }
}
