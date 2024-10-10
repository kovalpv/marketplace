package com.fastsoft.inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notebooks")
public class NotebooksController {


  @GetMapping
  public ResponseEntity<List<NotebookDto>> getProducts() {

    log.warn("start");

    var notebooks = List.of(
        new NotebookDto("Macbook pro 16.2", 10),
        new NotebookDto("Macbook air 13.6", 3)
    );

    notebooks.forEach(n -> {
      log.info("book: {}", n);
    });

    log.warn("ready");


    return ResponseEntity.ok(notebooks);
  }

  record NotebookDto(
      String name,
      Integer count
  ) {

  }

}
