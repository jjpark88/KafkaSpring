package com.example.kafkaspring.service;


import com.example.kafkaspring.model.MyModel;

import java.util.List;

public interface MyService {

    public MyModel save(MyModel model);
    public MyModel saveTrans(MyModel model);
    public MyModel saveEvent(MyModel model);

    public List<MyModel> findAll();
    public MyModel findById(Integer id);

    public void delete(Integer id);

    public void deleteTrans(Integer id);

    public void deleteEvent(Integer id);
}
