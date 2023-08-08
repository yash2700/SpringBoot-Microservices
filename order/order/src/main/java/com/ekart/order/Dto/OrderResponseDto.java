package com.ekart.order.Dto;

import com.ekart.order.entity.Product;
import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private long id;
    List<Product> products;
    private LocalDateTime localDateTime;
}
