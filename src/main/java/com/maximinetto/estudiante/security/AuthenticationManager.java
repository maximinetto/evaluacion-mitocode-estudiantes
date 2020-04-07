package com.maximinetto.estudiante.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
	String token = authentication.getCredentials().toString();

	String usuario;

	try {
	    usuario = jwtUtil.getUsernameFromToken(token);
	} catch (Exception e) {
	    usuario = null;
	}

	if (usuario != null && jwtUtil.validateToken(token)) {
	    Claims claims = jwtUtil.getAllClaimsFromToken(token);
	    
	    // esta linea extrae del Map[payload] el item "roles" y devuelve el contenido
	    List<String> rolesMap = claims.get("roles", List.class);

	    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
		    usuario, 
		    null, 
		    rolesMap.stream()
		            .map(authority -> new SimpleGrantedAuthority(authority))
		            .collect(Collectors.toList()));
	    
	    return Mono.just(auth);
	} else {
	    return Mono.error(new InterruptedException("Token no válido o ha expirado"));
	}
    }

}
