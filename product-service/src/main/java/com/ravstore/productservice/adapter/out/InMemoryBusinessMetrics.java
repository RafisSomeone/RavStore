package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.port.out.BusinessMetrics;

public class InMemoryBusinessMetrics implements BusinessMetrics {
  @Override
  public void productCreateSuccessIncrement() {}

  @Override
  public void productCreateFailIncrement() {}

  @Override
  public void productUpdateSuccessIncrement() {}

  @Override
  public void productUpdateFailIncrement() {}

  @Override
  public void productGetSuccessIncrement() {}

  @Override
  public void productGetFailIncrement() {}
}
