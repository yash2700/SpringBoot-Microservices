package com.ekart.order.Exceptions;

import com.ekart.order.entity.Order;
import com.ekart.order.enums.Errors;

public class OrdersNotFoundException extends RuntimeException{
    public OrdersNotFoundException(Errors message){
        super(message.toString());
    }
}
