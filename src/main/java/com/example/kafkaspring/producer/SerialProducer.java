package com.example.kafkaspring.producer;

import com.example.kafkaspring.model.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SerialProducer {

    ObjectMapper objectMapper = new ObjectMapper();
    @Qualifier("serialKafkaTemplate")
    private final KafkaTemplate<String, String> serialKafkaTemplate;

    public void sendMessage(MyMessage myMessage) throws JsonProcessingException {

        String jsonMessage = objectMapper.writeValueAsString(myMessage);
        serialKafkaTemplate.send("serial_topic", jsonMessage);
    }
}
