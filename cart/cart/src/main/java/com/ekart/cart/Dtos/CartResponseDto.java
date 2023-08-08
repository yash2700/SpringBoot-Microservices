package com.ekart.cart.Dtos;

import com.ekart.cart.entity.Product;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private String emailId;
    private List<Product> productList;
    private BigDecimal totalAmount;
}
