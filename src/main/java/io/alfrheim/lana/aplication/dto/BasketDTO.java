package io.alfrheim.lana.aplication.dto;


import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class BasketDTO {
  private String id;
  private List<ProductDTO> products;

  public BasketDTO() {
  }

  public static BasketDTO from(Basket basket) {
    BasketDTO result = new BasketDTO();
    result.setId(basket.idAsString());
    result.setProductsFrom(basket.productsAsList());
    return result;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setProductsFrom(List<Product> products) {
    this.products = products.stream()
        .map(ProductDTO::from)
        .collect(Collectors.toList());
  }

  public void setProducts(List<ProductDTO> products) {
    this.products = products;
  }

  public List<ProductDTO> getProducts() {
    return products;
  }
}
