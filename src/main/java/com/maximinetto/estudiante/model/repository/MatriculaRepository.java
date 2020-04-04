package com.maximinetto.estudiante.model.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.maximinetto.estudiante.model.entity.Matricula;

public interface MatriculaRepository extends ReactiveCrudRepository<Matricula, String>{

}
