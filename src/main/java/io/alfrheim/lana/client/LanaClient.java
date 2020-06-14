package io.alfrheim.lana.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.alfrheim.lana.aplication.dto.BasketDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Client
 */
public class LanaClient {

      private final String host;
      private int port;
      RestTemplate restTemplate = restTemplate();

      HttpHeaders headers = new HttpHeaders();

      public LanaClient(String host, int port) {
          this.host = host;
          this.port = port;
      }

      private RestTemplate restTemplate() {
          ObjectMapper mapper = new ObjectMapper();
          mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
          return new RestTemplateBuilder().build();
      }

      public ResponseEntity<String> removeBasket(String id) {
          HttpEntity<String> entity;
          entity = new HttpEntity<>(null, headers);
          return restTemplate.exchange(
                  createURLWithPort(String.format("/baskets/%s",id)),
                  HttpMethod.DELETE, entity, String.class);
      }

      public ResponseEntity<String> checkout(String id) {
          HttpEntity<String> entity;
          entity = new HttpEntity<>(null, headers);
          return restTemplate.exchange(
                  createURLWithPort(String.format("/baskets/%s/checkout",id)),
                  HttpMethod.POST, entity, String.class);
      }

      public BasketDTO addProduct(String basketId, String product) {
          HttpEntity<String> entity;
          entity = new HttpEntity<>(null, headers);
          return restTemplate.exchange(
                  createURLWithPort(String.format("/baskets/%s/addProduct/%s", basketId, product)),
                  HttpMethod.POST, entity, BasketDTO.class).getBody();
      }

      public ResponseEntity<BasketDTO> createNewBasket() {
          HttpEntity<String> entity = new HttpEntity<>(null, headers);
          return restTemplate.exchange(
                  createURLWithPort("/baskets/new"),
                  HttpMethod.POST, entity, BasketDTO.class);
      }

      private String createURLWithPort(String uri) {
          return host + port + uri;
      }
  }
