package io.alfrheim.lana.infrastructure.repository;

import io.alfrheim.lana.core.repository.BasketRepository;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;

import java.util.HashMap;
import java.util.Map;

public class InMemoryBasketRepository implements BasketRepository {
  private final Map<BasketId, Basket> db = new HashMap<>();
  @Override
  public void add(Basket basket) {
    db.put(basket.id(), basket);
  }

  @Override
  public Basket getBasketFrom(BasketId basketId) {
    return db.get(basketId);
  }

  @Override
  public void save(Basket basket) {
    db.replace(basket.id(), basket);
  }

  @Override
  public void remove(BasketId basketId) {
    db.remove(basketId);
  }
}
