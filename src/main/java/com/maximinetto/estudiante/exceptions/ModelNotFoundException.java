package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class ModelNotFoundException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ModelNotFoundException(String message, String causa) {
	super(message, causa, HttpStatus.NOT_FOUND);
    }
    

}
