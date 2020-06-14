package io.alfrheim.lana.core;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
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

    verify(basketRepository).add(basket);
    assertThat(basket).isEqualTo(BASKET);
  }
}