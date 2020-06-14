package io.alfrheim.lana.core.product;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class Product {
  private final ProductId id;
  private BigDecimal price;

  public Product(ProductId id) {
    this.id = id;
  }

  public Product(ProductId id, BigDecimal price) {
    this.id = id;
    this.price = price;
  }

  public String idAsString() {
    return this.id.asString();
  }

  public ProductId id() {
    return this.id;
  }

  public BigDecimal priceAsBigDecimal() {
    return this.price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Product product = (Product) o;

    return new EqualsBuilder()
        .append(id, product.id)
        .append(price, product.price)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(price)
        .toHashCode();
  }
}
