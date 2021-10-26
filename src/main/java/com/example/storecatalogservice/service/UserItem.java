package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserItem {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserItem")
    public User getUserItem(Long userId) {
        return restTemplate.getForObject("http://store-profile-service/profile/" + userId, User.class);
    }

    public User getFallbackUserItem(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("Name Not found");
        user.setEmail("Email Not Found");
        return user;
    }
}
