package com.maximinetto.estudiante.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.maximinetto.estudiante.model.service.UsuarioService;
import com.maximinetto.estudiante.security.AuthRequest;
import com.maximinetto.estudiante.security.AuthResponse;
import com.maximinetto.estudiante.security.ErrorLogin;
import com.maximinetto.estudiante.security.JWUtil;

import reactor.core.publisher.Mono;

@RestController()
public class LoginController {
    
    @Autowired
    private JWUtil util;
    
    @Autowired
    private UsuarioService service;
    
    @GetMapping("/login")
    public Mono<ResponseEntity<?>> login (@RequestBody AuthRequest request){
	return service.buscarPorUsuario(request.getUsername())
		      .map((userDetails) -> {
			  String token = util.generateToken(userDetails);
			 
			  Date expiration = util.getExpirationDateFromToken(token);
			  
			  return BCrypt.checkpw(request.getPassword(), userDetails.getPassword()) 
			        ? ResponseEntity.ok(new AuthResponse(token, expiration))
		                :  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				               .body(new ErrorLogin("Credenciales incorrectas", new Date()));
		      })
		      .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                            .build());
    }
    
}
