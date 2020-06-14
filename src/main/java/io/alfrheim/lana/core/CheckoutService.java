package io.alfrheim.lana.core;

import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.checkout.Amount;
import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;

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
    //if another discount is added, create a new class Discount and create discounts from db or by code
    discount = discount.add(twoGetOneFreePromotion(basket, discount))
        .add(bulkPurchasesDiscount(basket, discount));
    return discount;
  }

  private BigDecimal bulkPurchasesDiscount(Basket basket, BigDecimal discount) {
    Product tshirt = new Product(new ProductId("TSHIRT"));
    long numberOfTshirts = totalProductsOf(basket, tshirt);
    if(threeOrMore(numberOfTshirts)) {
      BigDecimal tshirtDiscount = calculateTwentyFivePercentDiscountForItemFor(tshirt, numberOfTshirts);
      discount = discount.add(tshirtDiscount);
    }
    return discount;
  }

  private BigDecimal calculateTwentyFivePercentDiscountForItemFor(Product tshirt, long numberOfTshirts) {
    BigDecimal tshirtPrice = productRepository.get(tshirt.id()).priceAsBigDecimal();
    BigDecimal twenty_five_percent = new BigDecimal("4");
    return tshirtPrice.divide(twenty_five_percent, RoundingMode.FLOOR)
        .multiply(new BigDecimal(numberOfTshirts));
  }

  private boolean threeOrMore(long numberOfTshirts) {
    return numberOfTshirts > 2;
  }

  private BigDecimal twoGetOneFreePromotion(Basket basket, BigDecimal discount) {
    Product pen = new Product(new ProductId("PEN"));
    long pens = totalProductsOf(basket, pen);
    if(moreThanTwo(pens)) {
      BigDecimal pensDiscount = calculateFiftyPercentDiscountOf(pen, pens);
      discount = discount.add(pensDiscount);
    }
    return discount;
  }

  private BigDecimal calculateFiftyPercentDiscountOf(Product product, long totalNumberOfProducts) {
    BigDecimal penPrice = productRepository.get(product.id()).priceAsBigDecimal();
    return new BigDecimal(totalNumberOfProducts)
        .divide(BigDecimal.valueOf(2), RoundingMode.FLOOR)
        .multiply(penPrice);
  }

  private boolean moreThanTwo(long numberOfPens) {
    return numberOfPens > 1;
  }

  private long totalProductsOf(Basket basket, Product pen) {
    return basket.productsAsList().stream()
        .filter(product -> product.equals(pen))
        .count();
  }

  private BigDecimal calculatePriceOf(Basket basket) {
    return basket.productsAsList().stream()
        .map(Product::id)
        .map(productRepository::get)
        .map(Product::priceAsBigDecimal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
