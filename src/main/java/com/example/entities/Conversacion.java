package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

public class Conversacion implements Serializable {

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

    @NotNull(message = "Le nombre d'assistants ne peut pas être null")
    @Min(value = 2, message = "Le nombre d'assistants ne peut pas etre inferieur a deux")
    @Max(value = 8, message = "Le nombre d'assistants ne peut pas etre superieur a huit")
    private int numeroAsistentes;

    @Future
    @NotNull(message = "La date ne peut pas être nul")
    private LocalDate fecha;

    @Future
    @NotNull(message = "L'heure ne peut pas être nul")
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private Modo modo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    // private String inmutable;

    // private void Nivel (String inmutable){
    //     this.inmutable=inmutable;
    // }
    // public String getInmutable(){
    //     return inmutable;
    // }
    // public void setInmutable(String inmutable){
    //     this.inmutable= inmutable;
    // }
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "conversacion")
    @JsonIgnore
    private List<Asistente> asistentes;
}
