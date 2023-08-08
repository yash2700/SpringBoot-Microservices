package com.ekart.Account.Exceptions;


import com.ekart.Account.enums.Errors;

public class AccountTypeException extends RuntimeException{
    public AccountTypeException(Errors message){
        super(message.toString());
    }
}
