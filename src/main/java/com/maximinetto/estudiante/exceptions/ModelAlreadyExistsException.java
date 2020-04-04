package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class ModelAlreadyExistsException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ModelAlreadyExistsException(String causa) {
	super("No se puede guardar el documento", causa, HttpStatus.CONFLICT);
    }

}
