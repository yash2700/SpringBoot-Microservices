package com.ekart.identityService.controller;

import com.ekart.identityService.entity.UserCredentials;
import com.ekart.identityService.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping(value = "/token")
    public ResponseEntity<String> getToken(@RequestBody UserCredentials userCredentials){
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmailId()
        ,userCredentials.getPassword()));
      if(authentication.isAuthenticated())
        return ResponseEntity.ok(authService.generateToken(userCredentials.getEmailId()));
      throw new RuntimeException("Invalid Details!");
    }

    @GetMapping(value ="/validate" )
    public ResponseEntity<Jws<Claims>> validateToken(@RequestParam("token")String token){
        return ResponseEntity.ok(authService.validateToken(token));
    }
}
