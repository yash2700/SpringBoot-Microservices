package com.ekart.cart.Exceptions;

import com.ekart.cart.enums.Errors;

public class ProductServiceNotFoundException extends RuntimeException{
    public ProductServiceNotFoundException(Errors message){
        super(message.toString());
    }
}
