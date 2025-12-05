package com.ravstore.productservice.application.port.out;

public interface MetricsService {
  void productCreateSuccessIncrement();

  void productCreateFailIncrement();

  void databaseCallFailIncrement();

  void productUpdateSuccessIncrement();

  void productUpdateFailIncrement();
}
