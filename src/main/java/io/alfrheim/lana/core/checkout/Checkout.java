package io.alfrheim.lana.core.checkout;

import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.Products;

import java.math.BigDecimal;
import java.util.List;

public class Checkout {
  private final Products products;
  private final Amount amount;

  public Checkout(Products products, Amount amount) {
    this.products = products;
    this.amount = amount;
  }

  public List<Product> productsAsList() {
    return this.products.asList();
  }

  public Amount amount() {
    return amount;
  }

  public BigDecimal amountAsBigDecimal() {
    return amount.asBigDecimal();
  }
}
