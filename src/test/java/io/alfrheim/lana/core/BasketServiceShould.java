package io.alfrheim.lana.core;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.InMemoryProductTestRepository;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.checkout.Amount;
import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;
import io.alfrheim.lana.core.product.Products;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceShould {

  private static final String BASKET_ID = UUID.randomUUID().toString();
  private static final Basket BASKET = new Basket(BasketId.from(BASKET_ID));
  public static final Product PEN = new Product(new ProductId("PEN"));
  public static final Product MUG = new Product(new ProductId("MUG"));
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private BasketRepository basketRepository;

  private BasketService basketService;
  private CheckoutService checkoutService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    checkoutService = new CheckoutService(new InMemoryProductTestRepository());
    basketService = new BasketService(idGenerator, basketRepository, checkoutService);
  }

  @Test
  public void create_a_new_basket() {
    given(idGenerator.next()).willReturn(BASKET_ID);

    Basket basket = basketService.create();

    verify(basketRepository).add(BASKET);
    assertThat(basket).isEqualTo(BASKET);
  }

  @Test
  public void add_product_to_basket() {
    given(basketRepository.getBasketFrom(BasketId.from(BASKET_ID))).willReturn(BASKET);

    Basket basket = basketService.addProduct(BasketId.from(BASKET_ID), MUG);

    Basket aBasketWithMug = aBasketWithMug();
    verify(basketRepository).save(aBasketWithMug);
    assertThat(basket).isEqualTo(aBasketWithMug);
  }

  @Test
  public void return_checkout() {
    given(basketRepository.getBasketFrom(BasketId.from(BASKET_ID))).willReturn(aBasketWithMug());

    Checkout checkout = basketService.checkout(BasketId.from(BASKET_ID));

    assertThat(checkout.productsAsList()).contains(MUG);
    assertThat(checkout.amountAsBigDecimal()).isEqualTo(new BigDecimal("7.5"));
  }

  @Test
  public void return_checkout_with_discounts() {
    Basket basket = new Basket(BasketId.from(BASKET_ID));
    basket.add(PEN);
    basket.add(PEN);
    given(basketRepository.getBasketFrom(BasketId.from(BASKET_ID))).willReturn(basket);

    Checkout checkout = basketService.checkout(BasketId.from(BASKET_ID));

    assertThat(checkout.productsAsList()).contains(PEN, PEN);
    assertThat(checkout.amountAsBigDecimal()).isEqualTo(new BigDecimal("5"));
  }

  private Basket aBasketWithMug() {
    Basket result = new Basket(BasketId.from(BASKET_ID));
    result.add(MUG);
    return result;
  }
}