package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ProductListRating {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackListRating")
    public ResponseEntity<List<Rating>> getListRating() {
        return restTemplate.exchange("http://store-rating-service/ratings/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {
                });
    }

    public List<Rating> getFallbackListRating() {

        return Collections.singletonList(new Rating(0L, 0));

    }
}
