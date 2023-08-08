package com.ekart.product.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private long quantity;
   private String imageUrl;
    private long ratingCount;
    private double totalRating;


    public double getOverallRating(){
        if(ratingCount>0)
            return totalRating/ratingCount;
        return 0.0;
    }
    private void addRating(double rating){
        totalRating+=rating;
        ratingCount++;
    }
}
