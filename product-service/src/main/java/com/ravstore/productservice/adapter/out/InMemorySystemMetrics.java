package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.port.out.SystemMetrics;

public class InMemorySystemMetrics implements SystemMetrics {
  @Override
  public void databaseCallFailIncrement() {}
}
