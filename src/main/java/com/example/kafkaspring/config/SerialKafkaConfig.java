package com.example.kafkaspring.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class SerialKafkaConfig {


    @Bean
    @Qualifier("serialConsumerFactory")
    public ConsumerFactory<String, Object> serialConsumerFactory(KafkaProperties serialKafkaProperties) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serialKafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, serialKafkaProperties.getConsumer().getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, serialKafkaProperties.getConsumer().getValueDeserializer());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @Qualifier("serialKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> serialKafkaListenerContainerFactory(
            @Qualifier("serialConsumerFactory")ConsumerFactory<String, Object> serialConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(serialConsumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);

        return factory;
    }

    @Bean
    @Qualifier("serailProducerFactory")
    public ProducerFactory<String, String> serailProducerFactory(KafkaProperties serialKafkaTemplate) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serialKafkaTemplate.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serialKafkaTemplate.getProducer().getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serialKafkaTemplate.getProducer().getValueSerializer());
        props.put(ProducerConfig.ACKS_CONFIG, serialKafkaTemplate.getProducer().getAcks());
        return new DefaultKafkaProducerFactory<>(props);
    }

    // KafkaTemplate Bean 설정
    @Bean
    @Qualifier("serialKafkaTemplate")
    public KafkaTemplate<String, String> serialKafkaTemplate(@Qualifier("serailProducerFactory") ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
