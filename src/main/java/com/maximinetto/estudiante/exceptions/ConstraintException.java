package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class ConstraintException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ConstraintException(String message, String causa) {
	super(message, causa, HttpStatus.BAD_REQUEST);
    }

}
