package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.*;
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

    public Catalog getProductById(Long productId) {
        List<Product> productList = new ArrayList<>();
        List<Catalog> catalogList = new ArrayList<>();

            Product product = restTemplate.getForObject("http://store-information-service/products/" + productId, Product.class);
            Rating rating = restTemplate.getForObject("http://store-rating-service/ratings/" + productId, Rating.class);




        return new Catalog(product.getId(),product.getTitle(), product.getDescription(), product.getPrice(), rating.getRating(), product.getImageURL());

    }

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



//        if (!ratings.getRatings().isEmpty()) {
//            ratingsList = ratings.getRatings();
//            System.out.println("");
//        } else {
//            System.out.println("Error because of nil");
//        }

//        assert products != null;
//        assert ratings != null;
//        for (Product prod : products.getProducts()) {
//            for (Rating rati : ratings.getRatings()) {
//
//                ratingsList.add( new Rating(rati.getProduct_id(), rati.getRating()));
//
//
//                while (prod.getId().equals(rati.getProduct_id())) {
//
//                    catalogList.add(new Catalog(prod.getId(),
//                            prod.getTitle(),
//                            prod.getDescription(),
//                            prod.getPrice(),
//                            rati.getRating(),
//                            prod.getImageURL()));
//                }
//            }
//        }

        return catalogList;
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
