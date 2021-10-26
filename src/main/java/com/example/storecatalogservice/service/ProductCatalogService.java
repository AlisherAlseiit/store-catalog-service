package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProductCatalogService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProductInfo productInfo;

    @Autowired
    ProductRatingInfo productRatingInfo;

    @Autowired
    ProductListRating productListRating;

    @Autowired
    UserCommentItem userCommenttItem;

    @Autowired
    UserCartItem userCartItem;

    @Autowired
    UserItem userItem;

    @Autowired
    UserRatingItem userRatingItem;

    @Autowired
    ProductList productList;


    public Catalog getProduct(Long productId) {
        List<Product> productList = new ArrayList<>();
        List<Catalog> catalogList = new ArrayList<>();

        Product product = productInfo.getProductItem(productId);
        Rating rating = productRatingInfo.getRating(productId);

        return new Catalog(product.getId(),product.getTitle(), product.getDescription(), product.getPrice(), rating.getRating(), product.getImageURL());

    }



    public List<Catalog> getProducts() {
        List<Catalog> catalogList = new ArrayList<>();
        List<Rating> ratingsList = new ArrayList<>();
        List<Product> produtcts = new ArrayList<>();


        ResponseEntity<List<Rating>> rateResponse = productListRating.getListRating();

        ResponseEntity<List<Product>> productResponse = productList.getListProduct();

        ratingsList = rateResponse.getBody();
        produtcts = productResponse.getBody();

        for(Product product : produtcts) {

            catalogList.add(new Catalog(product.getId(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    ratingsList.get(1).getRating(),
                    product.getImageURL()));
        }

        return catalogList;
    }




    public User getUserData(Long userId) {

        UserRating ratings = userRatingItem.getUserRatingItem(userId);

        User user = userItem.getUserItem(userId);

        UserCart cart = userCartItem.getUserCartItem(userId);

        UserComment userComment = userCommenttItem.getUserCommentItem(userId);


        user.setCartList(cart);
        user.setUserRating(ratings);
        user.setUserComment(userComment);

        return user;
    }








}
