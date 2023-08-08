package com.ekart.ApiGateway.exceptions;

import com.ekart.ApiGateway.enums.Errors;

public class InvalidDetailsException extends RuntimeException{
    public InvalidDetailsException(Errors message){
        super(message.toString());
    }
}
