package com.maximinetto.estudiante.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximinetto.estudiante.model.entity.Matricula;
import com.maximinetto.estudiante.model.service.MatriculaService;
import com.maximinetto.estudiante.validators.RequestValidator;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService service;
    
    @Autowired
    private RequestValidator validator;
    
    @PostMapping
    public Mono<ResponseEntity<Matricula>> registrar(@RequestBody Matricula matricula, 
	                                   final ServerHttpRequest request){
	return validator
		.validar(matricula)
		.flatMap(service::create)	
	        .map(m -> ResponseEntity
	             .created(URI.create(request
	                                  .getURI()
	                                  .toString() 
	                                  .concat("/") 
	                                  .concat(m.getId())
	                                )
                             )
	             .contentType(MediaType.APPLICATION_STREAM_JSON)
		     .body(m)
			            
		);
    }
    
}
