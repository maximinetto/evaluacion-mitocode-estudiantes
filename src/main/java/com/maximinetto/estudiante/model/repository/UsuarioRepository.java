package com.maximinetto.estudiante.model.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.maximinetto.estudiante.model.entity.Usuario;

import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String>{

    Mono<Usuario> findOneByUsuario(String usuario);

}
