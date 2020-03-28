package com.maximinetto.estudiante.model.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estudiantes")
public class Estudiante {

    @Transient
    private static Estudiante estudiante;
    
    @Id
    private String id;

    @NotEmpty(message = "Se debe especificar los nombres del estudiante")
    private String nombres;

    @NotEmpty(message = "Se debe especificar los apellidos del estudiante")
    private String apellidos;

    @Indexed(unique = true)
    @NotEmpty(message = "Se debe especificar el dni del estudiante")
    private String dni;

    @Min(value = 0, message = "La edad debe ser mìnimo de 0")
    private Double edad;

    private Estudiante() {

    }
    
    public static Estudiante builder(String nombres, String apellidos, String dni) {
        estudiante = new Estudiante()
          .setNombres(nombres)
          .setApellidos(apellidos)
          .setDni(dni);
        
	return estudiante;
    }

    public String getId() {
	return id;
    }

    public Estudiante setId(String id) {
	this.id = id;
	return this;
    }

    public String getNombres() {
	return nombres;
    }

    public Estudiante setNombres(String nombres) {
	this.nombres = nombres;
	return this;
    }

    public String getApellidos() {
	return apellidos;
    }

    public Estudiante setApellidos(String apellidos) {
	this.apellidos = apellidos;
	return this;
    }

    public String getDni() {
	return dni;
    }

    public Estudiante setDni(String dni) {
	this.dni = dni;
	return this;
    }

    public Double getEdad() {
	return edad;
    }

    public Estudiante setEdad(Double edad) {
	this.edad = edad;
	return this;
    }

}
