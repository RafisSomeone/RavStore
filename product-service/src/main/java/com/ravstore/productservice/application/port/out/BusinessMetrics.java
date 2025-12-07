package com.ravstore.productservice.application.port.out;

public interface BusinessMetrics {
  void productCreateSuccessIncrement();

  void productCreateFailIncrement();

  void productUpdateSuccessIncrement();

  void productUpdateFailIncrement();

  void productGetSuccessIncrement();

  void productGetFailIncrement();
}
