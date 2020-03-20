package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class UniqueConstraintException extends GlobalErrorException{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UniqueConstraintException(String message, String causa) {
	super(message, causa, HttpStatus.CONFLICT);
    }
    
    
}
