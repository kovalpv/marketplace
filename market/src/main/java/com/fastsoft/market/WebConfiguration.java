package com.fastsoft.market;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfiguration {

  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder.baseUrl("http://localhost:8082").build();
  }

}
