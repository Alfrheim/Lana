package io.alfrheim.lana.aplication.dto;

import io.alfrheim.lana.core.product.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ProductDTO {
  private String id;

  public static ProductDTO from(Product product) {
    ProductDTO result = new ProductDTO();
    result.setId(product.idAsString());
    return result;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId(String id) {
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

    ProductDTO that = (ProductDTO) o;

    return new EqualsBuilder()
        .append(id, that.id)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .toHashCode();
  }
}
