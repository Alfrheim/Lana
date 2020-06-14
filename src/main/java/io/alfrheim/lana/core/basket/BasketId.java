package io.alfrheim.lana.core.basket;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BasketId {
  private final String id;

  private BasketId(String id) {
    this.id = id;
  }

  public static BasketId from(String id) {
    return new BasketId(id);
  }

  public String asString() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BasketId basketId = (BasketId) o;

    return new EqualsBuilder()
        .append(id, basketId.id)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .toHashCode();
  }
}
