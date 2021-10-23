package com.example.storecatalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {


    private String title;
    private String description;
    private Double price;
    private double rating;
    private String imageURL;


}
