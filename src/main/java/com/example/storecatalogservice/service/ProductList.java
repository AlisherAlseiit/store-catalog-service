package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Product;
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
public class ProductList {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackListProduct")
    public ResponseEntity<List<Product>> getListProduct() {
        return restTemplate.exchange("http://store-information-service/products/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                });
    }

    public List<Product> getFallbackListProduct() {
        Product product = new Product();
        product.setId(0L);
        product.setTitle("Product Name Not Found");
        product.setDescription("Product Description Not Found");
        product.setImageURL("Product URL Not Found");
        product.setPrice(0.0);

        return Collections.singletonList(product);
    }
}
