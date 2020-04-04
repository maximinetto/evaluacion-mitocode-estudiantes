package com.maximinetto.estudiante.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.model.entity.Matricula;
import com.maximinetto.estudiante.model.repository.MatriculaRepository;
import com.maximinetto.estudiante.model.service.MatriculaService;

import reactor.core.publisher.Mono;

@Service
public class MatriculaServiceImpl extends GenericServiceImpl<Matricula, String> implements MatriculaService{

    @Autowired
    private MatriculaRepository repository;
    
    @Override
    public Mono<Matricula> create(Matricula matricula) {
	return save(matricula)
		.flatMap(m -> this.get(m.getId())
		);
    }

    @Override
    public ReactiveCrudRepository<Matricula, String> getRepository() {
	return repository;
    }
    
}
