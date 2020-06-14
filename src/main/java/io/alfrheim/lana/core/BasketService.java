package io.alfrheim.lana.core;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.product.Product;

public class BasketService {
  private final IdGenerator idGenerator;
  private final BasketRepository basketRepository;

  public BasketService(IdGenerator idGenerator, BasketRepository basketRepository) {
    this.idGenerator = idGenerator;
    this.basketRepository = basketRepository;
  }

  public Basket create() {
    Basket basket = createNewBasket();
    basketRepository.add(basket);
    return basket;
  }

  private Basket createNewBasket() {
    return new Basket(BasketId.from(idGenerator.next()));
  }

  public Basket addProduct(BasketId basketId, Product product) {
    Basket basket = basketRepository.getBasketFrom(basketId);
    basket.add(product);
    basketRepository.save(basket);
    return basket;
  }

}
