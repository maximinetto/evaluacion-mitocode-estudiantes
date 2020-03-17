package com.maximinetto.estudiante.commons;

import java.io.Serializable;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID>{

    @Override
    public Flux<T> getAll() {
	return getRepository().findAll();
    }

    @Override
    public Mono<T> get(ID id) {
	return getRepository().findById(id);
    }
    
    @Override
    public Mono<T> save(T entity) {
	return getRepository().save(entity);
    }

    @Override
    public Mono<Void> delete(ID id) {
	return getRepository().deleteById(id);
    }
    
    
    public abstract Mono<T> create(T entity);
    public abstract ReactiveCrudRepository<T, ID> getRepository();
    
}
