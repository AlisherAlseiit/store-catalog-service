package com.example.storecatalogservice.service;

import com.example.storecatalogservice.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getFallbackProductItem",
            threadPoolKey = "storeInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
            }

    )
    public Product getProductItem(Long productId) {
        return restTemplate.getForObject("http://store-information-service/products/" + productId, Product.class);
    }



    public Product getFallbackProductItem(Long productId) {

        Product product = new Product();
        product.setId(productId);
        product.setTitle("Product Name Not Found");
        product.setDescription("Product Description Not Found");
        product.setImageURL("Product URL Not Found");
        product.setPrice(0.0);

        return  product;
    }
}
