package com.maximinetto.estudiante.handlers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.maximinetto.estudiante.model.entity.Curso;
import com.maximinetto.estudiante.model.service.CursoService;

import reactor.core.publisher.Mono;

@Component
public class CursoHandler {
    
    
    @Value("${id.name}")
    private String id;
    
    @Autowired
    private CursoService service;
    
    public Mono<ServerResponse> listar(ServerRequest request){
	return ServerResponse.ok()
		.contentType(MediaType.APPLICATION_STREAM_JSON)
		.body(service.getAll(), Curso.class);
    }
    
    public Mono<ServerResponse> listarPorId(ServerRequest request){
	String idCurso = request.pathVariable(id);
	return service.get(idCurso)
		.flatMap( e -> ServerResponse.ok()
			   .contentType(MediaType.APPLICATION_STREAM_JSON)
			   .body(BodyInserters.fromValue(e))
		)
		.switchIfEmpty(ServerResponse.notFound().build());
	
    }
    
    public Mono<ServerResponse> registrar(ServerRequest request){
	Mono<Curso> cursoMono = request.bodyToMono(Curso.class);
	return cursoMono
		.flatMap(e -> service.create(e)
			)
		.flatMap(e -> ServerResponse.created(URI.create(request.uri().toString()
					.concat("/")
					.concat(e.getId())))
			 .contentType(MediaType.APPLICATION_STREAM_JSON)
			 .body(BodyInserters.fromValue(e))
			);
		
    }

}
