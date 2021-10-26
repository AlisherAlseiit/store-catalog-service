package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Rating;
import com.example.storecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRatingItem {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatingItem")
    public UserRating getUserRatingItem(Long userId) {
        return restTemplate.getForObject("http://store-rating-service/ratings/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRatingItem(Long userId) {

        List<Rating> ratings = new ArrayList<>();
        Rating rating = new Rating();
        rating.setRating(0.0);

        ratings.add(rating);

        return  new UserRating(ratings);

    }
}
