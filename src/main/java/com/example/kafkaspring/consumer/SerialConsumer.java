package com.example.kafkaspring.consumer;

import com.example.kafkaspring.model.MyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SerialConsumer {
//Seial producer 컨슈머

    @KafkaListener(
        topics = { "serial_topic" },
            groupId = "batch-serial-consumer-group",
        containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void accept(List<ConsumerRecord<String, String>> messages) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        messages.forEach(message -> {
            MyMessage myMessage;
            try{
                myMessage = objectMapper.readValue(message.value(), MyMessage.class);
            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
            System.out.println(myMessage);
             //System.out.println("ㄴ [Third Consumer] Value - " + myMessage + " / Offset - " + message.offset() + " / Partition - " + message.partition());
            }
        );


    }
    //단일 메세지 리스너
//    public void accept(ConsumerRecord<String, String> messages) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        MyMessage myMessage;
//        myMessage = objectMapper.readValue(messages.value(), MyMessage.class);
//        System.out.println(myMessage);
//    }
}
