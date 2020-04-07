package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAuthorized extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UserNotAuthorized(String causa) {
	super("Usuario no authorizado", causa, HttpStatus.UNAUTHORIZED);
    }
    
}
