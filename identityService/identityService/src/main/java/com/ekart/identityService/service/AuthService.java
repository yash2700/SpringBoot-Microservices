package com.ekart.identityService.service;

import com.ekart.identityService.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }
    public Jws<Claims> validateToken(String token){
        return jwtService.validateToken(token);
    }
}
