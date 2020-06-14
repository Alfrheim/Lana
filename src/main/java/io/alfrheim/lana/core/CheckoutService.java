package io.alfrheim.lana.core;

import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.checkout.Amount;
import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutService {
  private final ProductRepository productRepository;

  public CheckoutService(ProductRepository productRepository) {

    this.productRepository = productRepository;
  }

  public Checkout createFrom(Basket basket) {
    BigDecimal price = calculatePriceOf(basket);
    BigDecimal discounts = calculateDiscountsOf(basket);
    BigDecimal totalPrice = price.subtract(discounts);
    return new Checkout(basket.products(), Amount.from(totalPrice));
  }

  private BigDecimal calculateDiscountsOf(Basket basket) {
    BigDecimal discount = BigDecimal.ZERO;
    long numberOfPens = basket.productsAsList().stream().filter(product -> product.idAsString().equals("PEN")).count();
    if(numberOfPens > 1) {
      BigDecimal pensDiscount = new BigDecimal(numberOfPens).divide(BigDecimal.valueOf(2), RoundingMode.FLOOR).multiply(new BigDecimal("5"));
      discount = discount.add(pensDiscount);
    }
    long numberOfTshirts = basket.productsAsList().stream().filter(product -> product.idAsString().equals("PEN")).count();
    if(numberOfTshirts > 2) {
      BigDecimal tshirtDiscount = new BigDecimal(numberOfTshirts).multiply(new BigDecimal("5"));
      discount = discount.add(tshirtDiscount);
    }
    return discount;
  }

  private BigDecimal calculatePriceOf(Basket basket) {
    return basket.productsAsList().stream()
        .map(Product::id)
        .map(productRepository::get)
        .map(Product::priceAsBigDecimal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
