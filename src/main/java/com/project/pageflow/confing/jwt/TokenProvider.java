/**
 * The TokenProvider class is responsible for generating, parsing, and validating JWT tokens for authentication purposes.
 * It utilizes the io.jsonwebtoken library for JWT handling.
 */
package com.project.pageflow.confing.jwt;

import com.project.pageflow.util.Constant;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;


public class TokenProvider{

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private SecretKey key;
    /**
     * Setter method for the secret key used for JWT signing.
     * @param key The secret key to be set.
     */
    @Autowired
    public void setKey(SecretKey key){
        this.key = key;
    }

    /**
     * Generates a JWT token based on the given authentication details.
     * @param authentication The authentication object containing user details.
     * @return A JWT token as a String.
     */
    public String generateToken(Authentication authentication){

        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + Constant.getExpirationDateJwt());

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expirationDate)
                .setIssuedAt(currentDate)
                .compact();
    }

    /**
     * Retrieves the username from the given JWT token.
     * @param token The JWT token from which to retrieve the username.
     * @return The username extracted from the token.
     */
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    /**
     * Validates the given JWT token.
     * @param token The JWT token to be validated.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validate(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
