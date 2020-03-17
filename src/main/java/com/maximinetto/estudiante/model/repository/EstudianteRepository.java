package com.maximinetto.estudiante.model.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.model.entity.Estudiante;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, String>{
       
    Flux<Estudiante> findAllByOrderByEdadDesc();
    Mono<Estudiante> findByDni(String dni);

}
