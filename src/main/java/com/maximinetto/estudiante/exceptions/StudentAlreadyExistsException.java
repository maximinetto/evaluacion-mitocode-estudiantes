package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class StudentAlreadyExistsException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    

    public StudentAlreadyExistsException(String causa) {
	super(causa);
    }

    public StudentAlreadyExistsException(String message, String causa, HttpStatus status) {
	super(message, causa, status);
    }
    
}
