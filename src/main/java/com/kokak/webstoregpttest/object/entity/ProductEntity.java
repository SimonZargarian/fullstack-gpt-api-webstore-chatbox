package com.kokak.webstoregpttest.object.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String title;
    private double price;
    @Column(length = 1000) // Increase the size as necessary
    private String description;
    private String category;
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private RatingEntity rating;
}
