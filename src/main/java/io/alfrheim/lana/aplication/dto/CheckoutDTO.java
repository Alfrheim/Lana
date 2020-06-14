package io.alfrheim.lana.aplication.dto;

import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutDTO {
  private List<String> items;
  private AmountDTO total;

  public static CheckoutDTO from(Checkout checkout) {
    CheckoutDTO result = new CheckoutDTO();
    result.setItems(checkout.productsAsList().stream()
        .map(Product::idAsString)
        .collect(Collectors.toList()));
    result.setTotal(AmountDTO.from(checkout.amount()));
    return result;
  }

  public List<String> getItems() {
    return items;
  }

  public void setItems(List<String> items) {
    this.items = items;
  }

  public AmountDTO getTotal() {
    return total;
  }

  public void setTotal(AmountDTO total) {
    this.total = total;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CheckoutDTO that = (CheckoutDTO) o;

    return new EqualsBuilder()
        .append(items, that.items)
        .append(total, that.total)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(items)
        .append(total)
        .toHashCode();
  }
}
