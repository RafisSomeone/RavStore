package com.ravstore.productservice.adapter.out;

import com.ravstore.productservice.application.port.out.SystemMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class MeterSystemMetrics implements SystemMetrics {

  private final Counter databaseCallFail;

  public MeterSystemMetrics(MeterRegistry registry) {
    databaseCallFail =
        Counter.builder("ravstore.product-service.database.fail.count")
            .description("Number of database call fails")
            .register(registry);
  }

  @Override
  public void databaseCallFailIncrement() {
    databaseCallFail.increment();
  }
}
