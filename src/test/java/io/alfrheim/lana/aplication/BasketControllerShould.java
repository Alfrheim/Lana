package io.alfrheim.lana.aplication;

import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.aplication.dto.ProductDTO;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class BasketControllerShould {

  public static final String ID = "6fec8cf8-b9d1-44ca-8194-7326db75e3a0";
  private static final BasketId BASKET_ID = BasketId.from(ID);
  private static final Basket A_BASKET = new Basket(BASKET_ID);

  @Mock
  private BasketService basketService;

  private BasketController basketController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    basketController = new BasketController(basketService);
  }

  @Test
  public void return_basket_with_id_when_created() {
    given(basketService.create()).willReturn(A_BASKET);
    BasketDTO expectedBasket = new BasketDTO();
    expectedBasket.setId(ID);

    BasketDTO result = basketController.create();

    assertThat(result).isEqualToComparingFieldByField(expectedBasket);
  }

  @Test
  public void add_product_to_basket() {
    Basket aBasket = new Basket(BasketId.from(ID));
    aBasket.add(new Product("MUG"));
    given(basketService.addProduct(BasketId.from(ID), new Product("MUG"))).willReturn(aBasket);

    BasketDTO result = basketController.addProduct(ID, "MUG");

    assertThat(result.getProducts()).containsExactly(aMug());
  }

  private ProductDTO aMug() {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId("MUG");
    return productDTO;
  }

}
