package com.maximinetto.estudiante.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maximinetto.estudiante.exceptions.ConstraintException;

import reactor.core.publisher.Mono;

@Component
public class RequestValidator {

    @Autowired
    private Validator validator;
    
    public <T> Mono<T> validar(T obj){
	if(obj == null) {
	    return Mono.error(new IllegalArgumentException());
	}
	
	Set<ConstraintViolation<T>> violaciones = this.validator.validate(obj);
	if(violaciones == null || violaciones.isEmpty()) {
	    return Mono.just(obj);
	}
	
	System.out.println(violaciones);
	
	List<ConstraintException> exceptions = new ArrayList<ConstraintException>();
	violaciones.forEach(v -> {
	   ConstraintException c = new ConstraintException(v.getMessage(), "Petición malformada");   
	   exceptions.add(c);   
	});
	return Mono.error(exceptions.stream().findFirst().orElse(new ConstraintException("Error desconocido", 
		"Petición malformada")));
    }
    
}
