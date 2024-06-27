package ru.ivoligo.kafka.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listener(@Payload String message,
                         @Header(KafkaHeaders.OFFSET) long offset,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        System.out.printf("Received message: %s. Offset: %s, Partition: %s \n", message, offset, partition);
    }
}
