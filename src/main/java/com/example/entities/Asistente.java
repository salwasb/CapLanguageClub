package com.example.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asistentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asistente implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "Le prenom ne peut pas être nul")
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "Le nom ne peut pas être nul")
    @NotEmpty(message = "El apellido no puede estar vacío")
    private String apellidos;

    @NotNull(message = "L'email ne peut pas être nul")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La langue ne peut pas être nul")
     private Idioma idioma; 

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Le niveau ne peut pas être nul")
    private Nivel niveles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hebernateLazyInitializer", "handler"})
    private Conversacion conversacion;
}
