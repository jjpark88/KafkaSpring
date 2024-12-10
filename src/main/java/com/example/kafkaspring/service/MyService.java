package com.example.kafkaspring.service;


import com.example.kafkaspring.model.MyModel;

import java.util.List;

public interface MyService {

    public List<MyModel> findAll();
    public MyModel findById(Integer id);
    public MyModel save(MyModel model);
    public void delete(Integer id);
}