package io.alfrheim.lana.core;

import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;

public interface BasketRepository {
  void add(Basket basket);

  Basket getBasketFrom(BasketId basketId);

  void save(Basket basket);
}
