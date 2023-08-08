package com.ekart.product.Exceptions;

import com.ekart.product.enums.Errors;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Errors message){
        super(message.toString());
    }
}
