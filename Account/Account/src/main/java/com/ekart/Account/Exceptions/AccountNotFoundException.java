package com.ekart.Account.Exceptions;

import com.ekart.Account.enums.Errors;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(Errors message){
        super(message.toString());
    }
}
