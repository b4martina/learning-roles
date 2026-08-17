package com.example.AuthLearn.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
//created after changes in securityConfig
@Component
public class JWTGenerator {

    private static final  String JWT_SECRET_KEY = "keykeykeykeykeykeykeykeykey282828";

    private final SecretKey key =
            Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    public String generateToken(String username){
     //   String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstant.JWT_EXPIRATION);

//String  token = OAuth2AuthorizationServerProperties.Jwt.builder();

        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String token= Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 18000000)) // Updated from setExpiration()
                .signWith(key, Jwts.SIG.HS256)
                .compact();


        return token;
    }


    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }


    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token))
                && extractClaims(token).getExpiration().after(new Date());}




     //   return username.equals(extractUsername(token))
          //      && extractClaims(token).getExpiration().after(new Date());}


    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();}



}
