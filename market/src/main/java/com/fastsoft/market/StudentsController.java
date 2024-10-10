package com.fastsoft.market;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping({"/products"})
public class StudentsController {

  private final WebClient webClient;
  private final ObservationRegistry observationRegistry;

  @GetMapping
  public ResponseEntity<List<NotebookDto>> getProducts() {
    Observation observation = Observation.start("serviceCall", observationRegistry);
    log.warn("start");
    try {
      RestClient restClient = RestClient.create();
      final Mono<NotebookDto[]> response = webClient.get().uri("/notebooks")
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(NotebookDto[].class);
      final NotebookDto[] notebooks = response.block();
      for (var n : notebooks) {
        log.info("book: {}", n);
      }

      log.warn("end");
      return ResponseEntity.ok(Arrays.asList(notebooks));
    } finally {
      observation.stop();
    }

    
  }

  record NotebookDto(
      String name,
      Integer count
  ) {

  }

}
