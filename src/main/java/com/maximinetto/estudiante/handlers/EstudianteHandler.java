package com.maximinetto.estudiante.handlers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.maximinetto.estudiante.model.entity.Estudiante;
import com.maximinetto.estudiante.model.service.EstudianteService;
import com.maximinetto.estudiante.validators.RequestValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EstudianteHandler {

    @Value("${id.name}")
    private String id;
    
    @Autowired
    private RequestValidator validator;

    @Autowired
    private EstudianteService service;

    public Mono<ServerResponse> listar(ServerRequest request) {
	
	
	Optional<String> optionalParallel = request
		.queryParam("parallel")
		.filter(par -> Boolean.parseBoolean(par));
	
	Optional<String> optionalSortBy = request
	    .queryParam("sortBy")
	    .filter(sortBy -> sortBy.equals("age"));

	
	
	Flux<Estudiante> flujoFinal = service.listarByCriteria(optionalSortBy, optionalParallel);
	
	return ServerResponse.ok()
		             .contentType(MediaType.APPLICATION_STREAM_JSON)
		             .body(flujoFinal, Estudiante.class);
	
    }

    public Mono<ServerResponse> listarPorId(ServerRequest request) {
	String idEstudiante = request.pathVariable(id);
	return service.get(idEstudiante)
		      .flatMap(e -> ServerResponse
			               .ok()
			               .contentType(MediaType.APPLICATION_STREAM_JSON)
		                       .body(BodyInserters.fromValue(e))
		              )
		      .switchIfEmpty(ServerResponse
			               .notFound()
			               .build());

    }

    public Mono<ServerResponse> registrar(ServerRequest request) {
	Mono<Estudiante> estudianteMono = request.bodyToMono(Estudiante.class);
	return estudianteMono.flatMap(this.validator::validar)
		.flatMap(service::create)
		.flatMap(e -> 
			ServerResponse.created(URI.create(request.uri()
			              .toString()
			              .concat("/")
			              .concat(e.getId())))
			      .contentType(MediaType.APPLICATION_STREAM_JSON)
			      .body(BodyInserters.fromValue(e))
	        );

    }

    public Mono<ServerResponse> modificar(ServerRequest request) {
	Mono<Estudiante> estudianteMono = request.bodyToMono(Estudiante.class);
	return estudianteMono.flatMap(this.validator::validar)
		.flatMap(service::save)
		.flatMap(est -> ServerResponse.ok()
		                .contentType(MediaType.APPLICATION_STREAM_JSON)
		                .body(BodyInserters.fromValue(est))
		)
		.switchIfEmpty(ServerResponse.notFound().build());
    }
    
    public Mono<ServerResponse> eliminar(ServerRequest request) {
	String idEstudiante = request.pathVariable(id);
	
	return service.delete(idEstudiante)
		      .then(ServerResponse.noContent()
			                  .build());
    }
    
    

}
