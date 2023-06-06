package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

public class Conversacion implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @NotNull(message = "Le nom ne peut pas être null")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String titulo; 

    @NotNull(message = "Le lieu ne peut pas être null")
    @NotBlank(message = "Le lieu ne peut pas être vide")
    private String lugar;

    @Size(min = 4, message = "Le nombre de participants ne peut être moins que quatre et plus que huit")
    private int numeroAsistentes; 

    
    private LocalDate fecha;
    private LocalTime hora; 

    @Enumerated(EnumType.STRING)
    private Modo modo; 
    
    @Enumerated(EnumType.STRING)
     private Idioma idioma; 

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "conversacion")
    private Nivel nivel; 

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "conversacion")
    @JsonIgnore
    private List<Asistente> asistentes;
}
