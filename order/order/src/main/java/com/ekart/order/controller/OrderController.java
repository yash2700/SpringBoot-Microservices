package com.ekart.order.controller;

import com.ekart.order.Dto.InventoryCheckDto;
import com.ekart.order.Dto.OrderEntryDto;
import com.ekart.order.Dto.OrderResponseDto;
import com.ekart.order.ServiceImpl.OrderServiceImpl;
import com.ekart.order.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OrderController {
    private static final Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderServiceImpl orderService;
    @CircuitBreaker(name = "orderService",fallbackMethod = "orderFallBack")
    @PostMapping(value = "/order",consumes = "application/json",produces = "application/json")
    public ResponseEntity<OrderResponseDto> order(@Valid @RequestBody OrderEntryDto orderEntryDto,@RequestHeader("AUTHORIZATION")String  token)throws JsonProcessingException {
        System.out.println(orderEntryDto);
        return new ResponseEntity<>(orderService.order(orderEntryDto,token), HttpStatus.CREATED);
    }
    @CircuitBreaker(name = "orderService",fallbackMethod = "viewAllOrdersFallBack")
    @GetMapping(value = "/viewOrders",produces = "application/json")
    public ResponseEntity<List<Order>> viewAllOrders(@RequestParam String emailId,@RequestHeader("AUTHORIZATION")String  token ) throws JsonProcessingException {
        return new ResponseEntity<>(orderService.viewAllOrders(emailId,token),HttpStatus.OK);
    }
    @CircuitBreaker(name = "orderService",fallbackMethod = "cancelOrderFallBack")
    @DeleteMapping(value = "/cancelOrder",produces = "application/json")
    public ResponseEntity<String> cancelOrder(@RequestParam String emailId,@RequestParam Long orderId,@RequestHeader("AUTHORIZATION")String  token ){
        return new ResponseEntity<>(orderService.cancelOrder(emailId,orderId,token),HttpStatus.OK);
    }


    public ResponseEntity<OrderResponseDto> orderFallBack(OrderEntryDto orderEntryDto,String token,Throwable throwable){
        logger.error("----------------Order FallBack----------------");
        return new ResponseEntity<>(new OrderResponseDto(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<List<Order>> viewAllOrdersFallBack(String emailId,String token,Throwable throwable){
        logger.error("------------------View All Orders FallBack------------------------");
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<String> cancelOrderFallBack(String emailId,Long orderId,String token,Throwable throwable){
        logger.error("---------------cancel Order FallBack--------------------");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
