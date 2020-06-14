package io.alfrheim.lana.aplication.dto;


import io.alfrheim.lana.core.basket.Basket;

public class BasketDTO {
  private String id;

  public BasketDTO() {
  }

  public static BasketDTO from(Basket basket) {
    BasketDTO result = new BasketDTO();
    result.setId(basket.idAsString());
    return result;
  }


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
