package com.example.kafkaspring.producer;

import com.example.kafkaspring.common.CustomObjectMapper;
import com.example.kafkaspring.model.MyCdcMessage;
import com.example.kafkaspring.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyCdcProducer {

    CustomObjectMapper objectMapper = new CustomObjectMapper();


    private final KafkaTemplate<String, String> serialKafkaTemplate;

    public void sendMessage(MyCdcMessage message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);

        serialKafkaTemplate.send(
            Topic.MY_CDC_TOPIC,
                String.valueOf(message.getId()),
                jsonMessage
        );
    }
}
