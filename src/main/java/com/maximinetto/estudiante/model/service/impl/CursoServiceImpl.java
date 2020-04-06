package com.maximinetto.estudiante.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.exceptions.ModelNotFoundException;
import com.maximinetto.estudiante.exceptions.StudentAlreadyExistsException;
import com.maximinetto.estudiante.exceptions.UniqueConstraintException;
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
    
    @Override
    public Mono<Curso> save(Curso curso) {
	return repository.findByNombre(curso.getNombre())
		         .flatMap(est -> {
		             if(!est.getId().equals(curso.getId())) {
		        	 throw new UniqueConstraintException("No se puede salvar el curso",
		        		 "El nombre ya está solicitado por otro curso");
		             }
		             
		             return Mono.empty();
		         })
		         .then(get(curso.getId()))
		         .switchIfEmpty(Mono.error( 
		        	 new ModelNotFoundException("No se ha encontrado el curso", "No existe ese id")))
		         .then(super.save(curso));
	//super.save(entity);
    }

    @Override
    public Mono<Void> delete(String id) {
	return get(id)
		.switchIfEmpty( Mono.error(new ModelNotFoundException("No se puede eliminar el curso", 
	              "El curso no existe")))
		.flatMap(est -> super.delete(id));

    }

}
