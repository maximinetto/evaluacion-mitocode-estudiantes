package com.maximinetto.estudiante.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWUtil {

    @Value("${jjwt.secret}")
    private String secret;
    @Value("${jjwt.expirationTime}")
    private String expirationTime;
    
    public Claims getAllClaimsFromToken(String token) {
	return Jwts.parserBuilder()
	    .requireAudience(secret)
	    .build()
	    .parseClaimsJws(token)
	    .getBody();
    }
    
    public String getUsernameFromToken(String token) {
	return getAllClaimsFromToken(token).getSubject();
    }
    
    public Date getExpirationDateFromToken(String token) {
	return getAllClaimsFromToken(token).getExpiration();
    }
    
    public String generateToken(User user) {
	Map<String, Object> payload = new HashMap<>();
	payload.put("roles", user.getRoles());
	return doGenerateToken(payload, user.getUsername());
    }
    
    public boolean validateToken(String token) {
	return !isTokenExpired(token);
    }
    
    private String doGenerateToken(Map<String, Object> claims, String username) {
	long expirationTimeLong = Long.parseLong(expirationTime);
	
	final Date createdDate = new Date();
	final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
	
	SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
	
	return Jwts.builder()
		   .setClaims(claims)
		   .setSubject(username)
		   .setIssuedAt(createdDate)
		   .setExpiration(expirationDate)
		   .signWith(key)
		   .compact();
		   
    }
    
    private boolean isTokenExpired(String token) {
	final Date expiration = getExpirationDateFromToken(token);
	return expiration.before(new Date());
    }
}
