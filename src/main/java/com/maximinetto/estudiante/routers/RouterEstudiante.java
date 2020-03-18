package com.maximinetto.estudiante.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import com.maximinetto.estudiante.handlers.EstudianteHandler;

@Configuration
public class RouterEstudiante {
    
    @Bean
    public RouterFunction<ServerResponse> rutas(EstudianteHandler handler){
	return route(GET("/estudiantes"), handler::listar);
    }
}
