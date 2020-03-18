package com.maximinetto.estudiante.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import com.maximinetto.estudiante.handlers.EstudianteHandler;

@Configuration
public class RouterEstudiante {
    @Value("${id.name}")
    private String id;
    
    @Bean
    public RouterFunction<ServerResponse> rutas(EstudianteHandler handler){
	return route(GET("/estudiantes"), handler::listar)
		.andRoute(GET(String.format("/estudiantes/{%s}", id)), handler::listarPorId);
    }
    
    
}
