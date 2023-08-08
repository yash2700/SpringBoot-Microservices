package com.ekart.order.Exceptions;

import com.ekart.order.enums.Errors;

public class ProductNotAvailableException extends RuntimeException{
    public ProductNotAvailableException(Errors message){
        super(message.toString());
    }
}
