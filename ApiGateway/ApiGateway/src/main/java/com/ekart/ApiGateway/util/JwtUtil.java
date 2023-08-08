package com.ekart.ApiGateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Autowired
    RestTemplate restTemplate;
    private static final String SECRET="876234762378342783427387324893289328932893248734";

    public Jws<Claims> validateToken(final String token){
        Jws<Claims> claimsJws= Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
        return claimsJws;
    }




    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

