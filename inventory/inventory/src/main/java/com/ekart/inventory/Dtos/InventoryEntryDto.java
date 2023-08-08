package com.ekart.inventory.Dtos;

import com.ekart.inventory.Entity.Inventory;
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
public class InventoryEntryDto {
    @Min(value = 1,message = "{inventory.quantity.invalid}")
    private long quantity;
    @NotNull(message = "{inventory.productId.invalid}")
    private long productId;

    public static Inventory mapToInventory(InventoryEntryDto inventoryEntryDto){
        return Inventory.builder()
                .productId(inventoryEntryDto.getProductId())
                .isAvailable(true)
                .build();
    }
}

