package com.ekart.ApiGateway.exceptions;

import com.ekart.ApiGateway.enums.Errors;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(Errors message){
        super(message.toString());
    }
}
