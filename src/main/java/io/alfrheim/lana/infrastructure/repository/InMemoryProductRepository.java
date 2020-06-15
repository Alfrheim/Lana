package io.alfrheim.lana.infrastructure.repository;

import io.alfrheim.lana.core.repository.ProductRepository;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
  private final Map<ProductId, Product> db = new HashMap<>();

  public InMemoryProductRepository() {
    Product pen = new Product(new ProductId("PEN"), new BigDecimal("5"));
    Product tshirt = new Product(new ProductId("TSHIRT"), new BigDecimal("20"));
    Product mug = new Product(new ProductId("MUG"), new BigDecimal("7.5"));
    db.put(pen.id(), pen);
    db.put(tshirt.id(), tshirt);
    db.put(mug.id(), mug);
  }
  @Override
  public Product get(ProductId productId) {
    return db.get(productId);
  }
}
