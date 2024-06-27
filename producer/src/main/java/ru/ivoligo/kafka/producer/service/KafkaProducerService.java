package ru.ivoligo.kafka.producer.service;

import lombok.val;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(@Autowired KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {

        final ProducerRecord<String, String> record = buildRecord(message);

        val future = kafkaTemplate.send(record);
        future.whenComplete(((result, ex) -> {
            if (result != null) {
                handleSuccess(result);
            } else {
                handleFailure(ex);
            }
        }));

    }

    private ProducerRecord<String, String> buildRecord(String message) {

        return new ProducerRecord<>(topic, message);
    }

    private void handleFailure(Throwable ex) {

        System.out.println("Failed " + ex.getMessage());
    }

    private void handleSuccess(SendResult<String, String> result) {

        if(result == null || result.getRecordMetadata() == null) {
            throw new RuntimeException("Cannot!");
        }
        System.out.println("Success! \nTopic name:" + result.getRecordMetadata().topic()
                + "\nOffset: " + result.getRecordMetadata().offset()
                + "\nPartition: " + result.getRecordMetadata().partition());
    }
}
