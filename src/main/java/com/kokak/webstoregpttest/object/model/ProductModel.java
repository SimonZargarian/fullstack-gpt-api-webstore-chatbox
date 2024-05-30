package com.kokak.webstoregpttest.object.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingModel rating;
}
