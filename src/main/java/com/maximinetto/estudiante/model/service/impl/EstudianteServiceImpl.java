package com.maximinetto.estudiante.model.service.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.exceptions.GlobalErrorException;
import com.maximinetto.estudiante.exceptions.ModelNotFoundException;
import com.maximinetto.estudiante.exceptions.StudentAlreadyExistsException;
import com.maximinetto.estudiante.exceptions.UniqueConstraintException;
import com.maximinetto.estudiante.model.entity.Estudiante;
import com.maximinetto.estudiante.model.repository.EstudianteRepository;
import com.maximinetto.estudiante.model.service.EstudianteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstudianteServiceImpl extends GenericServiceImpl<Estudiante, String> implements EstudianteService {

    private EstudianteRepository repository;
    private Estudiante estudiante;

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository repository) {
	this.repository = repository;
    }

    @Override
    public Flux<Estudiante> getStudentsByAgeDesc() {
	return repository.findAllByOrderByEdadDesc();
    }

    @Override
    public Mono<Estudiante> create(Estudiante estudiante) {
	this.estudiante = estudiante;
	return guardarMono(new StudentAlreadyExistsException(
		String.format("Ya existe un estudiante con dni %s en el sistema.", estudiante.getDni()))); 
    }

    @Override
    public Mono<Estudiante> save(Estudiante estudiante) {
	return repository.findStudentsByDni(estudiante.getDni())
		         .flatMap(est -> {
		             if(!est.getId().equals(estudiante.getId())) {
		        	 throw new UniqueConstraintException("No se puede salvar el estudiante",
		        		 "El dni ya está solicitado por otro estudiante");
		             }
		             
		             return Mono.empty();
		         })
		         .then(super.save(estudiante));
	//super.save(entity);
    }

    @Override
    public Mono<Void> delete(String id) {
	return get(id).flatMap(est -> super.delete(id))
		      .switchIfEmpty( Mono.error(new ModelNotFoundException("No se puede eliminar el estudiante", 
			              "El estudiante no existe")));

    }

    @Override
    public ReactiveCrudRepository<Estudiante, String> getRepository() {
	return repository;
    }

    @Override
    public Flux<Estudiante> getStudentByDni(String dni) {
	return repository.findStudentsByDni(dni);
    }

    private Mono<Estudiante> guardarMono(GlobalErrorException exception) {
	return repository.findStudentsByDni(estudiante.getDni())
		         .hasElements()
		         .flatMap(checkearQueExisteGuardar(exception));
    }

    private Function<? super Boolean, ? extends Mono<? extends Estudiante>> 
                       checkearQueExisteGuardar(GlobalErrorException exception) {
	return hasElements -> {
	    if (hasElements) {
		throw exception;
	    }
	    else {
		return this.save(estudiante);
	    }
	};
    }
}
