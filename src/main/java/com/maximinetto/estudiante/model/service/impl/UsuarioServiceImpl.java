package com.maximinetto.estudiante.model.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

import com.maximinetto.estudiante.commons.GenericServiceImpl;
import com.maximinetto.estudiante.exceptions.ModelAlreadyExistsException;
import com.maximinetto.estudiante.model.entity.Rol;
import com.maximinetto.estudiante.model.entity.Usuario;
import com.maximinetto.estudiante.model.repository.UsuarioRepository;
import com.maximinetto.estudiante.model.service.UsuarioService;
import com.maximinetto.estudiante.security.User;

import reactor.core.publisher.Mono;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, String> implements UsuarioService{
    
    @Autowired
    private UsuarioRepository repository;

    @Override
    public Mono<User> buscarPorUsuario(String usuario) {

      Mono<Usuario> monoUsuario = repository.findOneByUsuario(usuario);
      
      return monoUsuario.flatMap( u ->{
	  return Mono.just(
	              new User(
	        	u.getUsuario(), 
	        	u.getClave(), 
	        	u.getEstado(), 
	        	u.getRoles()
	        	 .stream()
		         .map( r -> r.getNombre())
		         .collect(Collectors.toList())
		      )
	           );
      }
      			
	   
      );            
      
    }

    @Override
    public Mono<Usuario> create(Usuario usuario) {
	return repository.findOneByUsuario(usuario.getUsuario())
		.hasElement()
		.flatMap(e ->
		  e ? Mono.error(new ModelAlreadyExistsException("El usuario ya existe"))
		    : save(usuario)
		);
    }

    @Override
    public ReactiveCrudRepository<Usuario, String> getRepository() {
	return repository;
    }

  

}
