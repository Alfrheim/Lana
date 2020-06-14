package io.alfrheim.lana.configuration;

import io.alfrheim.lana.IdGenerator;
import io.alfrheim.lana.core.BasketRepository;
import io.alfrheim.lana.core.BasketService;
import io.alfrheim.lana.infrastructure.repository.InMemoryBasketRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
  public BasketService basketService(IdGenerator idGenerator, BasketRepository basketRepository) {
    return new BasketService(idGenerator, basketRepository);
  }

}
