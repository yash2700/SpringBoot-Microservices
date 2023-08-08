package com.ekart.cart.Dtos;

import com.ekart.cart.entity.Cart;
import com.ekart.cart.helper.RestTemplateHelper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntryDto {
    @Email(message = "{cart.email.invalid}")
    private String emailId;
    @NotNull(message ="{cart.productId.invalid}")
    @Min(value = 1)
    private long productId;
}
