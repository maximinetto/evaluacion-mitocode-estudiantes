package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalErrorException extends ResponseStatusException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String causa;

    public GlobalErrorException(String causa) {
	this("No se puede insertar el documento", causa, HttpStatus.CONFLICT);
    }

    public GlobalErrorException(String message, String causa, HttpStatus status) {
	super(status, message);
	this.causa = causa;
    }
    
    public String getCausa() {
	return causa;
    }

    public void setCausa(String causa) {
	this.causa = causa;
    }
    

}