package com.example.storecatalogservice.controller;


import com.example.storecatalogservice.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
@RequestMapping("/catalog")
public class ProductController {

    @Autowired
    private ProductCatalogService service;

    @GetMapping()
    private ResponseEntity<?> getProducts() {
        return  ResponseEntity.ok(Collections.singletonList(service.getProducts()));
    }

    @GetMapping("/{productId}")
    private ResponseEntity<?> getProduct(@PathVariable("productId") Long productId) {
//        Collections.singletonList(service.getProductById(productId));
        return  ResponseEntity.ok(Collections.singletonList(service.getProduct(productId)));
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserData(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(service.getUserData(userId));
    }


}
