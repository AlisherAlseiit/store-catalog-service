package com.example.storecatalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private Double rating;
    private String imageURL;


}
