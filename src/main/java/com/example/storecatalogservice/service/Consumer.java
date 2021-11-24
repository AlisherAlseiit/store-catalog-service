package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Product;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {

    @KafkaListener(topics = "store_test", groupId = "group_id")
    public void consume(Product product) {
        System.out.println("Consumed product " + product);

    }



}
