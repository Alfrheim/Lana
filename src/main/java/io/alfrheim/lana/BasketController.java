package io.alfrheim.lana;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketController {

  @PostMapping("/basket/new")
  public String create() {
    return "sometihng";
  }
}
