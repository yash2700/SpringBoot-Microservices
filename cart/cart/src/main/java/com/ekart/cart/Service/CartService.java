package com.ekart.cart.Service;

import com.ekart.cart.Dtos.CartEntryDto;
import com.ekart.cart.Dtos.CartResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CartService {
    String addToCart(CartEntryDto cartEntryDto);
    CartResponseDto getCart(String emailId,String token) throws JsonProcessingException;
}
