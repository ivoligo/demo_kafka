package ru.ivoligo.kafka.producer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ivoligo.kafka.producer.service.KafkaProducerService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {

    private final String RESULT_TEXT = "The message has been sent";
    private final KafkaProducerService producerService;

    @GetMapping("/send")
    public ResponseEntity<String> publish(@RequestParam("message") String message) {

        producerService.sendMessage(message);
        return ResponseEntity.ok(RESULT_TEXT);
    }

}
