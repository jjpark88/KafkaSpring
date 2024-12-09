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

    /*
        second템플릿은 string->string 설정 템플릿
        json으로 보내도 string으로 변환해서 받고, string으로 보내서 string으로 받기
        writeValueAsString 이거 쓰면 변환됨
     */
    ObjectMapper objectMapper = new ObjectMapper();
    @Qualifier("secondKafkaTemplate")
    private final KafkaTemplate<String, String> secondKafkaTemplate;

    public void sendMessage(MyMessage myMessage) throws JsonProcessingException {

        String jsonMessage = objectMapper.writeValueAsString(myMessage);
        System.out.println("test = " + jsonMessage);
        secondKafkaTemplate.send("serial_topic", jsonMessage);
    }
}
