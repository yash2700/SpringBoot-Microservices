package com.ekart.inventory.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCheckDto {
    @Min(value = 1,message = "Minimum quantity should be 1")
    private long quantity;
    @NotNull(message = "product Id must not be null.")
    private long productId;
}
