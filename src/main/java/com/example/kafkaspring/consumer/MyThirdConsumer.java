package com.example.kafkaspring.consumer;

import com.example.kafkaspring.model.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.runtime.ObjectMethods;
import java.util.List;

import static com.example.kafkaspring.model.Topic.MY_JSON_TOPIC;
import static com.example.kafkaspring.model.Topic.SINGLE_TOPIC;


@Component
public class MyThirdConsumer {

//    private final ObjectMapper objectMapper;
//
//    public MyThirdConsumer(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }

    @KafkaListener(
        topics = { "serial_topic" },
            groupId = "batch-serial-consumer-group",
        containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void accept(List<ConsumerRecord<String, String>> messages) {
//     System.out.println(messages);
        ObjectMapper objectMapper = new ObjectMapper();

        messages.forEach(message -> {
            MyMessage myMessage;
            try{
                myMessage = objectMapper.readValue(message.value(), MyMessage.class);
            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
                System.out.println("ㄴ [Third Consumer] Value - " + myMessage + " / Offset - " + message.offset() + " / Partition - " + message.partition());
            }
        );
    }
}
