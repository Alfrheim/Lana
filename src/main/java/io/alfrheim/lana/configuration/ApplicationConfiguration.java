package io.alfrheim.lana.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.repository.BasketRepository;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.core.CheckoutService;
import io.alfrheim.lana.core.repository.ProductRepository;
import io.alfrheim.lana.infrastructure.repository.InMemoryBasketRepository;
import io.alfrheim.lana.infrastructure.repository.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public IdGenerator idGenerator() {
    return new IdGenerator();
  }

  @Bean
  public BasketRepository basketRepository() {
    return new InMemoryBasketRepository();
  }

  @Bean
  public ProductRepository productRepository() {
    return new InMemoryProductRepository();
  }

  @Bean
  public BasketService basketService(IdGenerator idGenerator, BasketRepository basketRepository,
                                     CheckoutService checkoutService) {
    return new BasketService(idGenerator, basketRepository, checkoutService);
  }

  @Bean
  public CheckoutService checkoutService(ProductRepository productRepository) {
    return new CheckoutService(productRepository);
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return new MappingJackson2HttpMessageConverter(mapper);
  }
}
