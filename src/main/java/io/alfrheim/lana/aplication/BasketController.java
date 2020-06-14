package io.alfrheim.lana.aplication;

import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.core.BasketService;
import org.springframework.web.bind.annotation.PostMapping;
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
}
