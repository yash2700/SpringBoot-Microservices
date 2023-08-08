package com.ekart.order.Exceptions;

import com.ekart.order.enums.Errors;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(Errors message){
        super(message.toString());
    }
}
