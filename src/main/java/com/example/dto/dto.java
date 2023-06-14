package com.example.dto;
import java.time.LocalDate;
import java.time.LocalTime;

public class dto {
    
    private String titulo;
    private String lugar;
    private int numeroAsistentes;
    private LocalDate fecha;
    private LocalTime hora;

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



    
}
