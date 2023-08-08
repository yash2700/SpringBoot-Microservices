package com.ekart.product.Dtos;

import com.ekart.product.Entity.Product;
import lombok.*;
import java.math.BigDecimal;
import java.util.Base64;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDto {
    private long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private long quantity;
    private double rating;
    private String image;

    public static ProductResponseDto mapToProductResponse(Product product){
        return ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .rating(product.getOverallRating())
                .image(product.getImageUrl())
                .build();
    }
}
