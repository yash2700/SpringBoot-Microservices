package com.ekart.Account.Controller;

import com.ekart.Account.Dtos.*;
import com.ekart.Account.ServiceImpl.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
@Validated
public class AccountController {
    private static final Logger logger= LoggerFactory.getLogger(AccountController.class);
    @Autowired
    AccountServiceImpl accountService;

    @PostMapping(value = "/create",consumes = "application/json",produces = "application/json")
    public ResponseEntity<AccountResponseDto> createAccount(@Valid  @RequestBody AccountEntryDto accountEntryDto){
            return new ResponseEntity<>(accountService.createAccount(accountEntryDto), HttpStatus.CREATED);
    }
    @CircuitBreaker(name = "accountService",fallbackMethod = "getAllAccountsFallBack")
    @GetMapping(value = "/getAllAccounts",produces = "application/json")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(@RequestHeader("AUTHORIZATION") String token) throws JsonProcessingException {
        System.out.println(token);
        return new ResponseEntity<>(accountService.getAllAccounts(token),HttpStatus.OK);
    }
    @CircuitBreaker(name = "accountService",fallbackMethod = "getAccountByEmailIdFallBack")
    @GetMapping(value = "/getByEmailId",produces = "application/json")
    public ResponseEntity<AccountResponseDto> getAccountByEmailId(@RequestParam String emailId,@RequestHeader("AUTHORIZATION") String token){
        return new ResponseEntity<>(accountService.getAccountByEmailId(emailId,token),HttpStatus.OK);
    }

    public ResponseEntity<AccountResponseDto> getAccountByEmailIdFallBack(String emailId,String token,Throwable throwable){
        logger.info("--------------In FallBack Method--------------");
        return new ResponseEntity<>(new AccountResponseDto(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<List<AccountResponseDto>> getAllAccountFallback(String token,Throwable throwable){
        logger.error("----------------In FallBack Method---------------");
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
