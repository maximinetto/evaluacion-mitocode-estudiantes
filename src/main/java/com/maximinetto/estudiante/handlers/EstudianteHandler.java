package com.maximinetto.estudiante.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.maximinetto.estudiante.model.entity.Estudiante;
import com.maximinetto.estudiante.model.service.EstudianteService;

import reactor.core.publisher.Mono;

@Component
public class EstudianteHandler {
    
    @Value("${id.name}")
    private String id;
    
    @Autowired
    private EstudianteService service;
    
    public Mono<ServerResponse> listar(ServerRequest request){
	return ServerResponse.ok()
		.contentType(MediaType.APPLICATION_STREAM_JSON)
		.body(service.getAll(), Estudiante.class);
    }
    
    public Mono<ServerResponse> listarPorId(ServerRequest request){
	String idEstudiante = request.pathVariable(id);
	return service.get(idEstudiante)
		.flatMap( e -> ServerResponse.ok()
			   .contentType(MediaType.APPLICATION_STREAM_JSON)
			   .body(BodyInserters.fromValue(e))
		)
		.switchIfEmpty(ServerResponse.notFound().build());
	
    }
    
}
