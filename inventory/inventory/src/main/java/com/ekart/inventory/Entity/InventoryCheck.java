package com.ekart.inventory.Entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCheck {
   private long productId;
   private long quantity;
}
