package com.ekart.Account.ServiceImpl;

import com.ekart.Account.Dtos.*;
import com.ekart.Account.Entity.Account;
import com.ekart.Account.Entity.Address;
import com.ekart.Account.Exceptions.AccountAlreadyExistsException;
import com.ekart.Account.Exceptions.AccountNotFoundException;
import com.ekart.Account.Exceptions.AccountTypeException;
import com.ekart.Account.Repository.AccountRepository;
import com.ekart.Account.Service.AccountService;
import com.ekart.Account.enums.AccountType;
import com.ekart.Account.enums.Errors;
import com.ekart.Account.helper.RestTemplateHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private static final Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RestTemplateHelper restTemplateHelper;
    @Override
    public AccountResponseDto createAccount(AccountEntryDto accountEntryDto) {
        System.out.println(accountEntryDto);
        if(accountRepository.existsByEmailId(accountEntryDto.getEmailId())){
            throw new AccountAlreadyExistsException(Errors.ACCOUNT_ALREADY_EXISTS);
        }
        if(validateAccountType(accountEntryDto.getAccountType())) {
            Account account = AccountEntryDto.mapToAccount(accountEntryDto);
            account.setPassword(passwordEncoder.encode(accountEntryDto.getPassword()));
            AccountResponseDto accountResponseDto = AccountResponseDto.mapToResponseDto(account);
            accountRepository.save(account);
            logger.info("Account with id :" + account.getId() + " is created!");
            return accountResponseDto;
        }else
            throw  new AccountTypeException(Errors.INVALID_ACCOUNT_TYPE);
    }

    @Override
    public List<AccountResponseDto> getAllAccounts(String token) throws JsonProcessingException {
        List<AccountResponseDto> accountResponseDtos=new ArrayList<>();
        List<Account> accountList=accountRepository.findAll();
//        AccountResponseDto accountResponseDto=AccountResponseDto.mapToResponseDto(accountList.get(0));
//        return List.of(accountResponseDto);
                List<Address> addressList=restTemplateHelper.getAllAddress(token);
        System.out.println(addressList);
        for (Account account:accountList){
            AccountResponseDto accountResponseDto=AccountResponseDto.mapToResponseDto(account);
            accountResponseDto.setAddresses(new ArrayList<>());
            for (Address address:addressList){
                if(address.getEmailId().equals(account.getEmailId())){
                    accountResponseDto.getAddresses().add(address);
                }
            }
            accountResponseDtos.add(accountResponseDto);

        }
        return accountResponseDtos;
    }

    @Override
    public AccountResponseDto getAccountByEmailId(String emailId,String token) {
        if(!accountRepository.existsByEmailId(emailId)){
            throw new AccountNotFoundException(Errors.ACCOUNT_NOT_FOUND);
        }
        Account account=accountRepository.findByEmailId(emailId);
        List<Address> addresses=restTemplateHelper.getAddressByEmailId(emailId,token);
        System.out.println(addresses);
        System.out.println(addresses);
        AccountResponseDto accountResponseDto=AccountResponseDto.mapToResponseDto(account);
        accountResponseDto.setAddresses(addresses);
        return accountResponseDto;

    }


    public boolean validateAccountType(String accountType){
        for(AccountType accountType1:AccountType.values())
            if(accountType1.toString().equals(accountType))
                return true;
        return false;
    }
}
