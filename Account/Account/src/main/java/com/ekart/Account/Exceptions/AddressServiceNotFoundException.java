package com.ekart.Account.Exceptions;

import com.ekart.Account.enums.Errors;

public class AddressServiceNotFoundException extends RuntimeException{
    public AddressServiceNotFoundException(Errors message){
        super(message.toString());
    }
}
