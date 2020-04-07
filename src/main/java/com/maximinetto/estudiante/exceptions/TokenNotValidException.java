package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class TokenNotValidException extends GlobalErrorException{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TokenNotValidException(String causa) {
	super("Usuario no autorizado", causa, HttpStatus.UNAUTHORIZED);
    }

    
}
