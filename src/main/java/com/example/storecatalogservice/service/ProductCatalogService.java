package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    ProductInfo productInfo;

    @Autowired
    ProductRatingInfo productRatingInfo;

    public Catalog getProduct(Long productId) {
        List<Product> productList = new ArrayList<>();
        List<Catalog> catalogList = new ArrayList<>();

        Product product = productInfo.getProductItem(productId);
        Rating rating = productRatingInfo.getRating(productId);

        return new Catalog(product.getId(),product.getTitle(), product.getDescription(), product.getPrice(), rating.getRating(), product.getImageURL());

    }






    public Catalog getProductInformationByIdFallback(Long productId) {
        return new Catalog(1L, "no title", "no description", 0.0, 0.0, "no imageURL");
    }

    @HystrixCommand(fallbackMethod = "getFallbackProducts")
    public List<Catalog> getProducts() {
        List<Catalog> catalogList = new ArrayList<>();
        List<Rating> ratingsList = new ArrayList<>();
        List<Product> produtcts = new ArrayList<>();



        ResponseEntity<List<Rating>> rateResponse =
                restTemplate.exchange("http://store-rating-service/ratings/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Rating>>() {
                        });

        ResponseEntity<List<Product>> productResponse =
                restTemplate.exchange("http://store-information-service/products/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                        });

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



    @HystrixCommand(fallbackMethod = "getFallbackUserData")
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
