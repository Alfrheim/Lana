package io.alfrheim.lana.aplication;

import io.alfrheim.lana.aplication.dto.AmountDTO;
import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.aplication.dto.CheckoutDTO;
import io.alfrheim.lana.aplication.dto.ProductDTO;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.core.basket.Basket;
import io.alfrheim.lana.core.basket.BasketId;
import io.alfrheim.lana.core.checkout.Amount;
import io.alfrheim.lana.core.checkout.Checkout;
import io.alfrheim.lana.core.product.Product;
import io.alfrheim.lana.core.product.ProductId;
import io.alfrheim.lana.core.product.Products;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class BasketControllerShould {

  public static final String ID = "6fec8cf8-b9d1-44ca-8194-7326db75e3a0";
  private static final BasketId BASKET_ID = BasketId.from(ID);
  private static final Basket A_BASKET = new Basket(BASKET_ID);
  public static final Product MUG = new Product(new ProductId("MUG"));
  public static final Product TSHIRT = new Product(new ProductId("TSHIRT"));

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

    assertThat(result.getId()).isEqualTo(ID);
  }

  @Test
  public void add_product_to_basket() {
    Basket aBasket = new Basket(BasketId.from(ID));
    aBasket.add(MUG);
    given(basketService.addProduct(BasketId.from(ID), MUG)).willReturn(aBasket);

    BasketDTO result = basketController.addProduct(ID, "MUG");

    assertThat(result.getProducts()).containsExactly(aMug());
  }

  @Test
  public void return_checkout() {
    Products products = new Products();
    products.add(MUG);
    products.add(TSHIRT);
    Checkout checkout = new Checkout(products, Amount.from("25"));
    given(basketService.checkout(BasketId.from(ID))).willReturn(checkout);

    CheckoutDTO result = basketController.checkout(ID);

    assertThat(result).isEqualTo(aCheckout());
  }

  @Test
  public void remove_basket() {
    basketController.delete(ID);

    verify(basketService).remove(BasketId.from(ID));
  }

  private CheckoutDTO aCheckout() {
    CheckoutDTO result = new CheckoutDTO();
    result.setItems(Arrays.asList("MUG", "TSHIRT"));
    AmountDTO amount = new AmountDTO();
    amount.setAmount("25.00");
    result.setTotal(amount);
    return result;
  }

  private ProductDTO aMug() {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId("MUG");
    return productDTO;
  }

}
