package com.ekart.order.Exceptions;

import com.ekart.order.enums.Errors;

public class InventoryServiceNotFoundException extends RuntimeException{
    public InventoryServiceNotFoundException(Errors message){
        super(message.toString());
    }
}
