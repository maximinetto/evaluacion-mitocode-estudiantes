package com.maximinetto.estudiante.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.maximinetto.estudiante.handlers.CursoHandler;

@Configuration
public class RouterCurso {
    
    @Value("${id.name}")
    private String id;
    
    private String baseUri = "/cursos";
    
    @Bean
    public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler){
	return route(GET(baseUri), handler::listar)
		.andRoute(GET(String.format("%s/{%s}", baseUri, id)), handler::listarPorId)
		.andRoute(POST(baseUri), handler::registrar);
    }

}
