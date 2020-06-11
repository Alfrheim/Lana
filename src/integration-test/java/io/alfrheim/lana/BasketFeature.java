package io.alfrheim.lana;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LanaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasketFeature {

	private static final UUID SOME_RANDOM_UUID = UUID.fromString("6fec8cf8-b9d1-44ca-8194-7326db75e3a0");

	@MockBean
	UUIDGenerator uuidGenerator;

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();


	@Test
	public void generateBasket() throws JSONException {
	  given(uuidGenerator.generate()).willReturn(SOME_RANDOM_UUID);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/basket/new"),
				HttpMethod.POST, entity, String.class);

		String expected = "{id:\"6fec8cf8-b9d1-44ca-8194-7326db75e3a0\"}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}

