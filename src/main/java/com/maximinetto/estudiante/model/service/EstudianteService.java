package com.maximinetto.estudiante.model.service;

import com.maximinetto.estudiante.commons.GenericService;
import com.maximinetto.estudiante.model.entity.Estudiante;

import reactor.core.publisher.Flux;

public interface EstudianteService extends GenericService<Estudiante, String> {
    Flux<Estudiante> getStudentsByAgeDesc(); 
    Flux<Estudiante> getStudentByDni(String dni);
}
