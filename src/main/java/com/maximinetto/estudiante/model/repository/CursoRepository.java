package com.maximinetto.estudiante.model.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.model.entity.Curso;

import reactor.core.publisher.Flux;

public interface CursoRepository extends ReactiveCrudRepository<Curso, String>{
 
    Flux<Curso> findByNombre(String nombre);
}
