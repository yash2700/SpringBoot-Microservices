package com.ekart.order.Exceptions;

import com.ekart.order.enums.Errors;

public class ProductServiceNotFoundException extends RuntimeException{
    public ProductServiceNotFoundException(Errors message){
        super(message.toString());
    }
}
