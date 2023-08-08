package com.ekart.inventory.Service;

import com.ekart.inventory.Dtos.InventoryEntryDto;
import com.ekart.inventory.Entity.Inventory;
import com.ekart.inventory.Entity.InventoryCheck;
import lombok.Lombok;

import java.util.List;

public interface InventoryService {
    String addToInventory(InventoryEntryDto inventoryEntryDto);
    List<Long> getAvailableProducts(List<Long> productIds);
    String order(List<Long> productIds,String emailId,long orderId,String token);
    String cancelOrder(List<Integer> ids,Long orderId,String token);

    String deleteFromInventory(Long productId);
}
