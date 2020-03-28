package com.maximinetto.estudiante.model.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.model.entity.Estudiante;

import reactor.core.publisher.Flux;

public interface EstudianteRepository extends  ReactiveCrudRepository<Estudiante, String>{
       
    Flux<Estudiante> findAllByOrderByEdadDesc();
    
    @Query("{ 'dni': ?0 }")
    Flux<Estudiante> findStudentsByDni(String dni);
    
   

}
