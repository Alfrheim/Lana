package io.alfrheim.lana.core.repository;

import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;

public interface ProductRepository {
  Product get(ProductId productId);
}
