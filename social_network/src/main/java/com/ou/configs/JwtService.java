package com.ou.configs;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ou.pojo.Account;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    private final String SECRECT = "ajfipupieuqwpieuasipdhfajlbfljh3y012637018274hfajlsd";
    private final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    public String generateAccessToken(Account account){
        return Jwts.builder()
                .setSubject(String.format("%s,%s", account.getEmail(), account.getPassword()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRECT)
                .compact();
    }
}
