package com.maximinetto.estudiante.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document
public class Matricula {
    
    @Id
    private String id;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
    
    @NotNull(message = "El estudiante debe ser especificado")
    @DBRef
    private Estudiante estudiante;
    
    @NotNull(message = "Se debe especificar al menos un curso")
    @Size(min = 1, message = "Se debe especificar al menos un curso")
    @DBRef
    private List<Curso> cursos;
    
    private boolean estado;

    public Matricula() {
	estado = false;
    }
    
    public Matricula(LocalDateTime fechaMatricula, Estudiante estudiante, List<Curso> cursos, boolean estado) {
	super();
	fecha = fechaMatricula;
	this.estudiante = estudiante;
	this.cursos = cursos;
	this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
}
