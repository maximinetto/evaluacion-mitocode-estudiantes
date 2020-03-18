package com.maximinetto.estudiante.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private Map<String, Object> errorPropertiesMap;

    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes errorAttributes, ResourceProperties resourceProperties,
	    ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
	super(errorAttributes, resourceProperties, applicationContext);
	this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
	return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
	errorPropertiesMap = getErrorAttributes(request, false);

	System.out.println(errorPropertiesMap);
	
	HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	String status = String.valueOf(errorPropertiesMap.get("status"));
	
	final Map<String, Object> mapException = ponerExcepcionesGeneralesEnMap(status, request);
	
	switch (status) {
	    case "500":
		httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	    break;
	
	    default: 
		httpStatus = HttpStatus.BAD_REQUEST;
	}

	return ServerResponse.status(httpStatus).contentType(MediaType.APPLICATION_STREAM_JSON)
		.body(BodyInserters.fromValue(mapException));
    }

    private Map<String, Object> ponerExcepcionesGeneralesEnMap(String status, ServerRequest request) {
	final Map<String, Object> mapException = new HashMap<String, Object>();
	mapException.put("mensaje", errorPropertiesMap.get("message"));
	mapException.put("statusCode", status);
	mapException.put("ruta", request.uri());
	mapException.put("fecha-error", LocalDateTime.now());

	return mapException;
    }

}
