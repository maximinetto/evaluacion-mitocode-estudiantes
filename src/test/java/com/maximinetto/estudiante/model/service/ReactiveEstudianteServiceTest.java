package com.maximinetto.estudiante.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.maximinetto.estudiante.model.entity.Estudiante;
import com.maximinetto.estudiante.model.service.impl.EstudianteServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@Import(value = EstudianteServiceImpl.class)
@DataMongoTest
public class ReactiveEstudianteServiceTest {

    @Autowired
    EstudianteService service;

    @Autowired
    ReactiveMongoOperations operations;

    @BeforeEach
    public void setUp() {

	operations.collectionExists(Estudiante.class)
		.flatMap(exists -> exists ? operations.dropCollection(Estudiante.class) : Mono.just(exists))
		.flatMap(o -> operations.createCollection(Estudiante.class)).then().block();

    }

    @Test
    public void testObtenerTodosEstudiantesDebeSerCero() {
	List<Estudiante> estudiantes = service.getAll().collectList().block();
	assertThat(estudiantes).hasSize(0);

    }

    @Test
    public void testGuardarEstudianteDebeTenerElMismoDni() {
	final String dniExpected = "12381232";
	Estudiante estudiante = Estudiante.builder("Maximiliano", "Minetto", dniExpected);

	Estudiante estudianteActual = service.save(estudiante).block();

	assertThat(estudianteActual.getDni()).isEqualTo(dniExpected);
    }

    @Test
    public void testActualizarEstudianteDebeCambiarNombre() {
	final String nombresExpected = "Maximilian Gabriel";

	Estudiante estudiante = Estudiante.builder("Maximiliano", "Minetto", "2319233");

	Estudiante estudianteActual = service.save(estudiante)
		.doOnNext(e -> service.save(e.setNombres(nombresExpected))).block();

	assertThat(estudianteActual.getNombres()).contains(nombresExpected);
	assertThat(service.getAll().collectList().block()).hasSize(1);
    }

    @Test
    public void testStudentsListShouldBeOrderedByAge() {

	assertListStudentsHas4Elements();

	assertAgeIsSorted();

    }

    private void assertListStudentsHas4Elements() {
	Estudiante estudiante1 = Estudiante.builder("Maximiliano", "Minetto", "2319233").setEdad(25d);

	Estudiante estudiante2 = Estudiante.builder("Felipe Andrés", "Gonzales Barreto", "23311311")
		.setEdad(22d);

	Estudiante estudiante3 = Estudiante.builder("Carolina Lucía", "Fernández Izauralde", "42321232")
		.setEdad(27d);

	Estudiante estudiante4 = Estudiante.builder("Agustina Sofía", "López Bermudez", "36321926").setEdad(21d);

	List<Estudiante> estudiantes = Mono.zip(service.save(estudiante1), service.save(estudiante2),
		service.save(estudiante3), service.save(estudiante4)).then(service.getAll().collectList()).block();
	assertThat(estudiantes).hasSize(4);
    }

    private void assertAgeIsSorted() {
	Double[] edades = service.getStudentsByAgeDesc().collectList().block().stream()
		.map(e -> e.getEdad().doubleValue()).collect(Collectors.toList()).toArray(new Double[0]);
	assertThat(edades).isSortedAccordingTo((age1, age2) -> age2.compareTo(age1) );
    }

}
