package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.CartItem;
import com.example.storecatalogservice.model.UserCart;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCartItem {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getFallbackUserCartItem")
    public UserCart getUserCartItem(Long userId) {
        return restTemplate.getForObject("http://store-cart-service/cart/" + userId, UserCart.class);
    }

    public UserCart getFallbackUserCartItem(Long userId) {


        List<CartItem> cartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setId(0L);
        cartItem.setProduct_id(0L);
        cartItem.setUser_id(userId);

        cartItems.add(cartItem);

        return new UserCart(cartItems);
    }
}
