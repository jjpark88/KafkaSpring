package com.example.kafkaspring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyMessage {
    private int id;
    private int age;
    private String name;
    private String content;
}
