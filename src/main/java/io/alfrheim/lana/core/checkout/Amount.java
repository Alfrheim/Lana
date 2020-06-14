package io.alfrheim.lana.core.checkout;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class Amount {
  private BigDecimal amount;

  private Amount(BigDecimal amount) {
    this.amount = amount;
  }

  public static Amount from(String amount) {
    return new Amount(new BigDecimal(amount));
  }

  public static Amount from(BigDecimal amount) {
    return new Amount(amount);
  }

  @Override
  public String toString() {
    return amount.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Amount amount1 = (Amount) o;

    return new EqualsBuilder()
        .append(amount, amount1.amount)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(amount)
        .toHashCode();
  }

  public BigDecimal asBigDecimal() {
    return this.amount;
  }
}
