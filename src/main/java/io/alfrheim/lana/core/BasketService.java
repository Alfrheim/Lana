package io.alfrheim.lana.core;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.repository.BasketRepository;

public class BasketService {
  private final IdGenerator idGenerator;
  private final BasketRepository basketRepository;
  private final CheckoutService checkoutService;

  public BasketService(IdGenerator idGenerator, BasketRepository basketRepository, CheckoutService checkoutService) {
    this.idGenerator = idGenerator;
    this.basketRepository = basketRepository;
    this.checkoutService = checkoutService;
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

  public Checkout checkout(BasketId basketId) {
    Basket basket = basketRepository.getBasketFrom(basketId);
    return checkoutService.createFrom(basket);
  }

  public void remove(BasketId basketId) {
    basketRepository.remove(basketId);
  }
}
