package com.ekart.identityService.config;

import com.ekart.identityService.entity.Account;
import com.ekart.identityService.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account= accountRepository.findByEmailId(username);
        if (account.isEmpty()){
            throw  new RuntimeException();
        }
        return account.get();
    }
}
