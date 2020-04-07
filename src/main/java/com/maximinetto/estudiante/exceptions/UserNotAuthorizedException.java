package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UserNotAuthorizedException(String causa) {
	super("Usuario no authorizado", causa, HttpStatus.UNAUTHORIZED);
    }
    
}
