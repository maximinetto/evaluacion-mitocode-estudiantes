package com.maximinetto.estudiante.model.service;

import com.maximinetto.estudiante.commons.GenericService;
import com.maximinetto.estudiante.model.entity.Usuario;
import com.maximinetto.estudiante.security.User;

import reactor.core.publisher.Mono;

public interface UsuarioService extends GenericService<Usuario, String>{
    Mono<User> buscarPorUsuario(String usuario);
}
