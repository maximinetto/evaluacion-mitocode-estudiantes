package com.maximinetto.estudiante.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.exceptions.StudentAlreadyExistsException;
import com.maximinetto.estudiante.model.entity.Curso;
import com.maximinetto.estudiante.model.repository.CursoRepository;
import com.maximinetto.estudiante.model.service.CursoService;

import reactor.core.publisher.Mono;

@Service
public class CursoServiceImpl extends GenericServiceImpl<Curso, String> implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Override
    public ReactiveCrudRepository<Curso, String> getRepository() {
	return repository;
    }

    @Override
    public Mono<Curso> create(Curso curso) {
	return repository.findByNombre(curso.getNombre()).hasElements().flatMap( hasElements -> 
	    hasElements ?  
		    	  Mono.error(new StudentAlreadyExistsException("El nombre del curso ya existe"))
		    	: repository.save(curso)
	);	
    }

}
