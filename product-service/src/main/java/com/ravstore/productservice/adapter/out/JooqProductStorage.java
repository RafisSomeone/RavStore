package com.ravstore.productservice.adapter.out;

import static com.ravstore.productservice.jooq.Tables.PRODUCTS;

import com.ravstore.productservice.application.dto.ProductDraft;
import com.ravstore.productservice.application.port.out.ProductStorage;
import com.ravstore.productservice.domain.Money;
import com.ravstore.productservice.domain.Product;
import com.ravstore.productservice.jooq.tables.records.ProductsRecord;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

@Slf4j
public class JooqProductStorage implements ProductStorage {

  private final DSLContext dsl;

  public JooqProductStorage(DSLContext dsl) {
    this.dsl = dsl;
  }

  private static Product toDomainProduct(ProductsRecord record) {
    Currency currency = Currency.getInstance(record.getCurrencyCode());
    Money price = Money.fromMinor(record.getAmountMinor(), currency);

    return new Product(record.getId(), record.getName(), price);
  }

  @Override
  public Product create(ProductDraft productDraft) {
    var record =
        dsl.insertInto(PRODUCTS)
            .set(PRODUCTS.NAME, productDraft.name())
            .set(PRODUCTS.AMOUNT_MINOR, productDraft.price().toMinor())
            .set(PRODUCTS.CURRENCY_CODE, productDraft.price().currency().getCurrencyCode())
            .returning()
            .fetchSingle();

    return toDomainProduct(record);
  }

  @Override
  public Optional<Product> update(Product product) {
    return dsl.update(PRODUCTS)
        .set(PRODUCTS.NAME, product.name())
        .set(PRODUCTS.AMOUNT_MINOR, product.price().toMinor())
        .set(PRODUCTS.CURRENCY_CODE, product.price().currency().getCurrencyCode())
        .set(PRODUCTS.UPDATED_AT, DSL.currentOffsetDateTime())
        .where(PRODUCTS.ID.eq(product.id()))
        .returning()
        .fetchOptional()
        .map(JooqProductStorage::toDomainProduct);
  }

  @Override
  public Optional<Product> get(UUID id) {
    return dsl.selectFrom(PRODUCTS)
        .where(PRODUCTS.ID.eq(id))
        .fetchOptional()
        .map(JooqProductStorage::toDomainProduct);
  }
}
