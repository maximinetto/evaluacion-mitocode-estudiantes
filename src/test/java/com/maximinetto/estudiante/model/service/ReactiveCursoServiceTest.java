package com.maximinetto.estudiante.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.maximinetto.estudiante.model.entity.Curso;
import com.maximinetto.estudiante.model.service.impl.CursoServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Import(CursoServiceImpl.class)
@DataMongoTest
public class ReactiveCursoServiceTest {

    @Autowired
    CursoService service;
    
    @Autowired
    ReactiveMongoOperations operations;
    
    @BeforeEach
    public void setUp() {
	operations.collectionExists(Curso.class)
	.flatMap(exists -> exists ? operations.dropCollection(Curso.class) : Mono.just(exists))
	.flatMap(o -> operations.createCollection(Curso.class)).then().block();
    }
    
    @Test
    public void testObtenerTodosCursosDebeSerCero() {
	List<Curso> cursos = service.getAll().collectList().block();
	assertThat(cursos).hasSize(0);
    }
    
    @Test
    public void testGuardarCursoDebeTenerElMismoNombre() {
	final String cursoExpected = "Seguridad Informática";
	Curso curso = new Curso();
	curso.setNombre(cursoExpected);
	curso.setSiglas("SI");
	
	Curso cursoActual = service.save(curso).block();
	assertThat(cursoActual.getNombre()).isEqualTo(cursoExpected);
    }
    
    @Test
    public void testGuardarCursoDebeCambiarEstado() {
	final boolean estadoExpected = true;
	Curso curso = new Curso();
	curso.setNombre("Páginas web");
	curso.setSiglas("PW");
	Curso cursoActual = service.save(curso)
		.doOnNext( c -> c.setEstado(true) ).block();
	
	assertThat(cursoActual.isEstado()).isEqualTo(estadoExpected);
	assertThat(service.getAll().collectList().block()).hasSize(1);
	
    }
    
    @Test
    public void testGuardarCursosDebeTener3Cursos() {
	assertDebeTener3Cursos();
    }
    
    private void assertDebeTener3Cursos() {
	final int sizeExpected = 3;
	
	Curso curso1 = new Curso();
	curso1.setNombre("Páginas web");
	curso1.setSiglas("PW");
	
	Curso curso2 = new Curso();
	curso2.setNombre("Seguridad Informática");
	curso2.setSiglas("SI");
	
	Curso curso3 = new Curso();
	curso3.setNombre("Arquitectura de software");
	curso3.setSiglas("AS");
	
	Mono.zip(service.save(curso1), service.save(curso2), service.save(curso3)).block();
	
	assertThat(service.getAll().collectList().block()).hasSize(sizeExpected);
    }
}
