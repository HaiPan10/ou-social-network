package com.ou.configs;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ou.pojo.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtService {
    private final String SECRECT = "ajfipupieuqwpieuasipdhfajlbfljh3y012637018274hfajlsd";
    private final long HOUR = 24;
    private final long MINUTE = 60;
    private final long SECOND = 60;
    private final long MILISECOND = 1000;
    private final long EXPIRE_DURATION = HOUR * MINUTE * SECOND * MILISECOND;

    private  static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    public String generateAccessToken(Account account){
        return Jwts.builder()
                .setSubject(String.format("%s,%s", account.getId(), account.getEmail()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRECT)
                .compact();
    }

    public boolean isValidAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRECT).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }
         
        return false;
    }

    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRECT)
                .parseClaimsJws(token)
                .getBody();
    }
}
