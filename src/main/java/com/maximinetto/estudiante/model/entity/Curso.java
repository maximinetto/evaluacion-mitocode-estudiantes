package com.maximinetto.estudiante.model.entity;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")
public class Curso {
    
    @Id
    private String id;
    @NotEmpty( message = "Se debe especificar el nombre del curso" )
    private String nombre;
    @NotEmpty( message = "Se debe especificar las siglas del curso" )
    private String siglas;
    
    private boolean estado = false;
    
    public Curso() {
	this(null, "", "");
    }

    public Curso(String id, String nombre, String siglas) {
	this.id = id;
	this.nombre = nombre;
	this.siglas = siglas;
	this.estado = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
