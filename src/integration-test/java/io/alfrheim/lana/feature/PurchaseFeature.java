package io.alfrheim.lana.feature;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.LanaApplication;
import io.alfrheim.lana.aplication.dto.BasketDTO;
import io.alfrheim.lana.client.LanaClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LanaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PurchaseFeature {

	private static final String SOME_RANDOM_UUID = UUID.randomUUID().toString();

	@MockBean
	IdGenerator idGenerator;

	@LocalServerPort
	private int port;

	LanaClient lanaClient;


	@Before
	public void setUp() throws Exception {
		lanaClient = new LanaClient("http://localhost:", port);
	}

	@Test
	public void can_purchase_different_products() throws JSONException {
		given(idGenerator.next()).willReturn(SOME_RANDOM_UUID);

		String expected = "{items:[\"PEN\",\"TSHIRT\", \"MUG\"], total:{ amount: \"32.50\"}}";

		ResponseEntity<String> response = lanaClient.createNewBasket();
		JSONObject jsonObject = new JSONObject(response.getBody());
		String basketId = jsonObject.getString("id");

		lanaClient.addProduct(basketId, "PEN");
		lanaClient.addProduct(basketId, "TSHIRT");
		lanaClient.addProduct(basketId, "MUG");

		ResponseEntity<String> checkout = lanaClient.checkout(basketId);
		ResponseEntity<Void> removeBasket = lanaClient.removeBasket(basketId);

		JSONAssert.assertEquals(expected, checkout.getBody(), false);
		assertTrue(removeBasket.getStatusCode().is2xxSuccessful());
	}

}
