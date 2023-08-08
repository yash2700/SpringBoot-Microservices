package com.ekart.inventory.Exceptions;

import com.ekart.inventory.enums.Error;

public class ProductServiceNotFoundException extends RuntimeException{
    public ProductServiceNotFoundException(Error message){
        super(message.toString());
    }
}
