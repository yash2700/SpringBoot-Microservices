package com.ekart.product.Dtos;

import com.ekart.product.Entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntryDto {
    @NotNull(message = "{product.name.invalid}")
    private String productName;

    @NotNull
    @NotBlank(message = "{product.description.invalid}")
    private String description;

    @Min(value = 1,message = "{product.price.invalid}")
    private BigDecimal price;

    @Min(value = 1,message = "{product.quantity.invalid}")
    private long quantity;
    @NotBlank(message = "{product.image.invalid}")
    private String imageUrl;

    public static Product mapToProductEntity(ProductEntryDto productEntryDto) throws IOException {
        return Product.builder()
                .productName(productEntryDto.getProductName())
                .description(productEntryDto.getDescription())
                .price(productEntryDto.getPrice())
                .quantity(productEntryDto.getQuantity())
                .imageUrl(productEntryDto.getImageUrl())
                .build();
    }


}
