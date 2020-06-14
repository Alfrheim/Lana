package io.alfrheim.lana.core.basket;

public class Basket {
  private final BasketId basketId;

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
}
