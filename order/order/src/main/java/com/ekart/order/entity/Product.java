package com.ekart.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    private long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private long quantity;
    private double rating;
    private String image;
}
