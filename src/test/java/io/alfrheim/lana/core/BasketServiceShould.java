package io.alfrheim.lana.core;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceShould {

  private static final String BASKET_ID = UUID.randomUUID().toString();
  private static final Basket BASKET = new Basket(BasketId.from(BASKET_ID));
  @Mock
  private IdGenerator idGenerator;
  @Mock
  private BasketRepository basketRepository;

  private BasketService basketService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    basketService = new BasketService(idGenerator, basketRepository);
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

    Basket basket = basketService.addProduct(BasketId.from(BASKET_ID), new Product("MUG"));

    Basket A_BASKET_WITH_MUG = aBasketWithMug();
    verify(basketRepository).save(A_BASKET_WITH_MUG);
    assertThat(basket).isEqualTo(A_BASKET_WITH_MUG);
  }

  private Basket aBasketWithMug() {
    Basket result = new Basket(BasketId.from(BASKET_ID));
    result.add(new Product("MUG"));
    return result;
  }
}