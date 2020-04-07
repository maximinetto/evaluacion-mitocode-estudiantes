package com.maximinetto.estudiante.security;

import java.util.Date;

public class AuthResponse {
    
    private String token;
    private Date expiration;
    
    public AuthResponse(String token, Date expiration) {
	super();
	this.token = token;
	this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    
    
    
    
}
