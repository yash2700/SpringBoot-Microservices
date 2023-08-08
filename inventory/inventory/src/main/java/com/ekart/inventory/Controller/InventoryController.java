package com.ekart.inventory.Controller;

import com.ekart.inventory.Dtos.InventoryEntryDto;
import com.ekart.inventory.Entity.InventoryCheck;
import com.ekart.inventory.ServiceImpl.InventoryServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {
    private static final Logger logger= LoggerFactory.getLogger(InventoryController.class);
    @Autowired
    InventoryServiceImpl inventoryService;

    @PostMapping(value = "/add",consumes = "application/json")
    public ResponseEntity<String> addToInventory(@Valid @RequestBody InventoryEntryDto inventoryEntryDto){
        return new ResponseEntity<>(inventoryService.addToInventory(inventoryEntryDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/isAvailable",consumes = "application/json",produces = "application/json")
    public ResponseEntity<List<Long>> getAvailableProducts(@RequestParam List<Long> productsList){
    return new ResponseEntity<>(inventoryService.getAvailableProducts(productsList),HttpStatus.OK);
    }
    @CircuitBreaker(name = "inventoryService",fallbackMethod = "orderFallback")
    @PostMapping(value = "/order",produces = "application/json",consumes = "application/json")
    public ResponseEntity<String> order(@RequestParam List<Long> productsList,@RequestParam String emailId,@RequestParam long orderId,@RequestHeader("AUTHORIZATION")String token){
        return new ResponseEntity<>(inventoryService.order(productsList,emailId,orderId,token),HttpStatus.OK);
    }
    @CircuitBreaker(name = "inventoryService",fallbackMethod = "cancelOrderFallBack")
    @PutMapping(value = "/cancelOrder",consumes = "application/json")
    public ResponseEntity<String> cancelOrder(@RequestParam List<Integer> ids,@RequestParam Long orderId,@RequestHeader("AUTHORIZATION")String token){
        return new ResponseEntity<>(inventoryService.cancelOrder(ids,orderId,token),HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{productId}")
    public ResponseEntity<String> deleteFromInventory(@PathVariable Long productId){
        return new ResponseEntity<>(inventoryService.deleteFromInventory(productId),HttpStatus.OK);
    }
    public ResponseEntity<String> orderFallBack(List<Long> productList,String emailId,Long orderId,String token,Throwable throwable){
        logger.error("-----------------order Fall Back--------------------");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> cancelOrderFallBack(List<Integer> ids,Long orderId,String token,Throwable throwable){
        logger.error("---------------------cancel Order Fall Back--------------------");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
