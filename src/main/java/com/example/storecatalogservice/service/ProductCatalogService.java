package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductCatalogService {

    @Autowired
    private RestTemplate restTemplate;

    public Catalog getProductById(Long productId) {
        List<Product> productList = new ArrayList<>();


            Product product = restTemplate.getForObject("http://store-information-service/products/" + productId, Product.class);
            Rating rating = restTemplate.getForObject("http://store-rating-service/ratings/" + productId, Rating.class);


        return new Catalog(product.getTitle(), product.getDescription(), product.getPrice(), rating.getRating(), product.getImageURL());

    }


    public User getUserData(Long userId) {

        UserRating ratings = restTemplate.getForObject("http://store-rating-service/ratings/users/" + userId, UserRating.class);

        User user = restTemplate.getForObject("http://store-profile-service/profile/" + userId, User.class);

        UserCart cart = restTemplate.getForObject("http://store-cart-service/cart/" + userId, UserCart.class);

        UserComment userComment = restTemplate.getForObject("http://store-comment-service/comments/" + userId, UserComment.class);


        user.setCartList(cart);
        user.setUserRating(ratings);
        user.setUserComment(userComment);

        return user;
    }
}
