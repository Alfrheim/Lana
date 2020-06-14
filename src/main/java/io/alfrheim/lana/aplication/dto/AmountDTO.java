package io.alfrheim.lana.aplication.dto;

import io.alfrheim.lana.core.checkout.Amount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.DecimalFormat;

public class AmountDTO {
  private String amount;
  public static final DecimalFormat amountFormat = new DecimalFormat("#,###.00");

  public static AmountDTO from(Amount amount) {
    AmountDTO result = new AmountDTO();
    result.setAmount(amountFormat.format(amount.asBigDecimal()));
    return result;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AmountDTO amountDTO = (AmountDTO) o;

    return new EqualsBuilder()
        .append(amount, amountDTO.amount)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(amount)
        .toHashCode();
  }
}
