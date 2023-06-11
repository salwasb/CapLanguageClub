package com.example.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Le prenom ne peut pas être vide")
    private String nombre;

    @NotNull(message = "Le nom ne peut pas être nul")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String apellidos;

    private String imagenAsistente;


    @NotNull(message = "L'email ne peut pas être nul")
    @Pattern(regexp = "(?i)^[A-Z0-9._%+-]+@cap\\.com$", message = "L'e-mail doit avoir le domaine @cap.com")
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La langue ne peut pas être nul")
     private Idioma idioma; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Conversacion conversacion;

    int count = 0;
    public void increment(){
    count++;
    }
    public void decrement(){
    count--;
    }
}
