package com.example.kafkaspring.model;

import lombok.Data;

@Data
public class MyMessage {
    private int id;
    private int age;
    private String name;
    private String content;
}