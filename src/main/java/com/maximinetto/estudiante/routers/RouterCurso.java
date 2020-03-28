package com.maximinetto.estudiante.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.maximinetto.estudiante.Constants;
import com.maximinetto.estudiante.handlers.CursoHandler;

@Configuration
public class RouterCurso {
       
    private String baseUri = "/cursos";
    private String baseUriWithID = String.format("%s/{%s}", baseUri, Constants.id);
    
    @Bean
    public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler){
	return route(GET(baseUri), handler::listar)
		.andRoute(GET(baseUriWithID), handler::listarPorId)
		.andRoute(POST(baseUri), handler::registrar)
		.andRoute(PUT(baseUri), handler::modificar)
		.andRoute(DELETE(baseUriWithID), handler::eliminar);
    }

}
