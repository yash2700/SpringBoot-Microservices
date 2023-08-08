package com.ekart.Account.Exceptions;

import com.ekart.Account.enums.Errors;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(Errors message){
        super(message.toString());
    }
}
