package com.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "conversaciones")
public class Conversacion{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    private String titulo; 
    private String lugar;
    private int numeroMaxAsistentes; 

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @DateTimeFormat(pattern = "hh:mm")
    private LocalTime hora; 

    @Enumerated(EnumType.STRING)
    private Modo modo; 

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Idioma idioma; 

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Nivel nivel; 
}
