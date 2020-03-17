package com.maximinetto.estudiante.exceptions;

import org.springframework.http.HttpStatus;

public class CourseAlreadyExistsException extends GlobalErrorException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CourseAlreadyExistsException(String causa) {
	super(causa);
    }



    public CourseAlreadyExistsException(String message, String causa, HttpStatus status) {
	super(message, causa, status);
    }
    
}
