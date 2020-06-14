package io.alfrheim.lana.core.product;

import java.util.ArrayList;
import java.util.List;

public class Products {
  private List<Product> products = new ArrayList<>();
  public void add(Product product) {
    products.add(product);
  }

  public List<Product> asList() {
    return products;
  }
}
