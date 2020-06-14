package io.alfrheim.lana.aplication;

import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.aplication.dto.CheckoutDTO;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

  private final BasketService basketService;

  public BasketController(BasketService basketService) {
    this.basketService = basketService;
  }

  @PostMapping("/baskets/new")
  public BasketDTO create() {
    return BasketDTO.from(basketService.create());
  }

  @PostMapping("/baskets/{id}/addProduct/{productId}")
  public BasketDTO addProduct(@PathVariable String id, @PathVariable String productId) {
    Basket basket = basketService.addProduct(BasketId.from(id), new Product(new ProductId(productId)));
    return BasketDTO.from(basket);
  }

  @PostMapping("/baskets/{id}/checkout")
  public CheckoutDTO checkout(@PathVariable String id) {
    return CheckoutDTO.from(basketService.checkout(BasketId.from(id)));
  }
}
