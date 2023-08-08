package com.ekart.cart.controller;


import com.ekart.cart.Dtos.CartEntryDto;
import com.ekart.cart.Dtos.CartResponseDto;
import com.ekart.cart.Service.CartService;
import com.ekart.cart.ServiceImpl.CartServiceImpl;
import com.ekart.cart.entity.Cart;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
    private static final Logger logger= LoggerFactory.getLogger(CartController.class);
   @Autowired
   CartServiceImpl cartService;

   @PostMapping(value = "/addToCart",produces = "application/json",consumes = "application/json")
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartEntryDto cartEntryDto){
        return new ResponseEntity<>(cartService.addToCart(cartEntryDto), HttpStatus.ACCEPTED);
   }
   @CircuitBreaker(name = "cartService",fallbackMethod = "getCartByEmailIdFallBack")
   @GetMapping(value = "/getByEmailId",produces = "application/json")
    public ResponseEntity<CartResponseDto> getCartByEmailId(@RequestParam String emailId,@RequestHeader("AUTHORIZATION") String token) throws JsonProcessingException {
       return new ResponseEntity<>(cartService.getCart(emailId,token),HttpStatus.OK);
   }
   public ResponseEntity<CartResponseDto> getCartByEmailIdFallBack(String emailId,String token,Throwable throwable){
       logger.error("---------------get cart by emailID Fall Back---------------------");
       return new ResponseEntity<>(new CartResponseDto(),HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
