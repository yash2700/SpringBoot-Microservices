package com.ekart.cart.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private long quantity;
    private double rating;
    private String image;
}
