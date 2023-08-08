package com.ekart.Account.Service;

import com.ekart.Account.Dtos.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountEntryDto accountEntryDto);
    List<AccountResponseDto> getAllAccounts(String token) throws JsonProcessingException;
    AccountResponseDto getAccountByEmailId(String emailId,String token);

}
