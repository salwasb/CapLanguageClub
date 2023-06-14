package com.example.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    // @email(regexp = /^[a-zA-Z0-9._%+-]{2,6}@(cap)\.com$/)
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "La langue ne peut pas être nul")
     private Idioma idioma; 

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Conversacion> conversacion;

}
