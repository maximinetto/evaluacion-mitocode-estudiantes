package com.maximinetto.estudiante.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.model.entity.Estudiante;
import com.maximinetto.estudiante.model.repository.EstudianteRepository;
import com.maximinetto.estudiante.model.service.EstudianteService;

import reactor.core.publisher.Flux;

public class EstudianteServiceImpl extends GenericServiceImpl<Estudiante, String> implements EstudianteService{

    private EstudianteRepository repository;
    
    @Autowired
    public EstudianteServiceImpl(EstudianteRepository repository) {
	this.repository = repository;
    }
    
    @Override
    public Flux<Estudiante> getStudentsByAgeDesc() {
	return repository.findAllByOrderByEdadDesc();
    }

    @Override
    public ReactiveCrudRepository<Estudiante, String> getRepository() {
	return repository;
    }

}
