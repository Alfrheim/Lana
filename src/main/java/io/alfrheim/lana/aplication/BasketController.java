package io.alfrheim.lana.aplication;

import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.product.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

  private final BasketService basketService;

  public BasketController(BasketService basketService) {
    this.basketService = basketService;
  }

  @PostMapping("/basket/new")
  public BasketDTO create() {
    return BasketDTO.from(basketService.create());
  }

  @PostMapping("/basket/{id}/addProduct")
  public BasketDTO addProduct(@PathVariable String id, @RequestBody String product) {
    Basket basket = basketService.addProduct(BasketId.from(id), new Product(product));
    return BasketDTO.from(basket);
  }
}
