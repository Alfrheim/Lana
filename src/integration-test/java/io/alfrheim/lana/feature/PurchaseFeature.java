package io.alfrheim.lana.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.LanaApplication;
import io.alfrheim.lana.aplication.dto.BasketDTO;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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

	RestTemplate restTemplate = restTemplate();

	HttpHeaders headers = new HttpHeaders();

	private RestTemplate restTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return new RestTemplateBuilder().build();
	}

	@Test
	public void can_purchase_different_products() throws JSONException {
		given(idGenerator.next()).willReturn(SOME_RANDOM_UUID);

		String expected = "{items:[\"PEN\",\"TSHIRT\", \"MUG\"], total:{ amount: \"32.50\"}}";

		ResponseEntity<BasketDTO> response = createNewBasket();

		addProduct(response.getBody().getId(), "PEN");
		addProduct(response.getBody().getId(), "TSHIRT");
		addProduct(response.getBody().getId(), "MUG");

		ResponseEntity<String> checkout = checkout(response.getBody().getId());
		ResponseEntity<String> removeBasket = removeBasket(response.getBody().getId());

		JSONAssert.assertEquals(expected, checkout.getBody(), false);
		assertTrue(removeBasket.getStatusCode().is2xxSuccessful());
	}

	private ResponseEntity<String> removeBasket(String id) {
		HttpEntity<String> entity;
		entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(
				createURLWithPort(String.format("/baskets/%s",id)),
				HttpMethod.DELETE, entity, String.class);
	}

	private ResponseEntity<String> checkout(String id) {
		HttpEntity<String> entity;
		entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(
		createURLWithPort(String.format("/baskets/%s/checkout",id)),
		HttpMethod.POST, entity, String.class);
	}

	private BasketDTO addProduct(String basketId, String product) {
		HttpEntity<String> entity;
		entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(
				createURLWithPort(String.format("/baskets/%s/addProduct/%s", basketId, product)),
				HttpMethod.POST, entity, BasketDTO.class).getBody();
	}

	private ResponseEntity<BasketDTO> createNewBasket() {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(
		createURLWithPort("/baskets/new"),
		HttpMethod.POST, entity, BasketDTO.class);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
