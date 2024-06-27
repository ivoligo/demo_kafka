package ru.ivoligo.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String addresses;

    @Bean
    public NewTopic myTopic() {

        return TopicBuilder
                .name(topic)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> config = new HashMap<>() {
            {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, addresses);
                put(ProducerConfig.BATCH_SIZE_CONFIG, 3);
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            }
        };
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {

        return new KafkaTemplate<>(producerFactory);
    }

}
