package io.alfrheim.lana.core.basket;

import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.Products;

import java.util.List;

public class Basket {
  private final BasketId basketId;
  private Products products = new Products();

  public Basket(BasketId basketId) {
    this.basketId = basketId;
  }

  public String idAsString() {
    return basketId.asString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Basket basket = (Basket) o;

    return new org.apache.commons.lang3.builder.EqualsBuilder()
        .append(basketId, basket.basketId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
        .append(basketId)
        .toHashCode();
  }

  public BasketId id() {
    return basketId;
  }

  public void add(Product product) {
    products.add(product);
  }

  public List<Product> productsAsList() {
    return products.asList();
  }
}
