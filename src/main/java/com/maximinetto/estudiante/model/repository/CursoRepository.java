package com.maximinetto.estudiante.model.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.model.entity.Curso;

import reactor.core.publisher.Mono;

public interface CursoRepository extends ReactiveCrudRepository<Curso, String>{
 
    Mono<Curso> findByNombre(String nombre);
}
