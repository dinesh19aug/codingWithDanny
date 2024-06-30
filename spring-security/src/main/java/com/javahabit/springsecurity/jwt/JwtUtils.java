package com.javahabit.springsecurity.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils implements Serializable {
    private static final long serialVersionUID = 342414840687873249L;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    public String getJwtDetailsFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }else {
            return null;
        }
    }

    public String generateToken(UserDetails userDetails){
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .subject(userName)
                .signWith(getSigningKey())
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationInMs)))
                .issuedAt(new Date(System.currentTimeMillis()))
                //.claims()
                .compact();
    }

    private Key getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserNameFromJwtToken(String jwtToken){

        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject();

    }

    public boolean validateJwtToken(String jwtToken){
        try{
            log.info("Validating Token: {}" , jwtToken);
            Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build().parseSignedClaims(jwtToken);
             return true;
        }catch (MalformedJwtException me){
            log.error("JWT Token {} is invalid", jwtToken);
        }catch (ExpiredJwtException jex){
            log.error("JWT Token {} is expired", jwtToken);
        }catch (UnsupportedJwtException jue){
            log.error("JWT Token {} is not supported", jwtToken);
        }catch (IllegalArgumentException ex){
            log.error("JWT claims is empty");
        }
        return false;
    }


}

