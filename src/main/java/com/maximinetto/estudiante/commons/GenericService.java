package com.maximinetto.estudiante.commons;

import java.io.Serializable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService<T, ID extends Serializable> {
    
    Mono<T> save(T entity);
    Mono<Void> delete(ID id);
    Mono<T> get(ID id);
    Flux<T> getAll();
    Mono<T> create(T entity);
    
}
