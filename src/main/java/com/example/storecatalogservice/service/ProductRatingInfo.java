package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackRating")
    public Rating getRating(Long productId) {
        return restTemplate.getForObject("http://store-rating-service/ratings/" + productId, Rating.class);
    }

    public Rating getFallbackRating(Long productId) {
        Rating rating = new Rating();

        rating.setProduct_id(productId);
        rating.setRating(0);

        return  rating;
    }
}
