package com.example.dto;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.entities.Idioma;
import com.example.entities.Nivel;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class dto {
    
    private String titulo;
    private String lugar;
    private int numeroAsistentes;
    private LocalDate fecha;
    private LocalTime hora;
    private Nivel nivel;
    private Idioma idioma;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public int getNumeroAsistentes() {
        return numeroAsistentes;
    }
    public void setNumeroAsistentes(int numeroAsistentes) {
        this.numeroAsistentes = numeroAsistentes;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHora() {
        return hora;
    }
    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    @Enumerated(EnumType.STRING)
    @JsonProperty("idioma")
    public Idioma getIdioma() {
        return idioma;
    }

    @Enumerated(EnumType.STRING)
    @JsonProperty("nivel")
    public Nivel getNivel() {
        return nivel;
    }


    
}
