package com.example.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "idiomas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Idioma implements Serializable{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String idioma;

    @OneToMany(fetch = FetchType.LAZY, cascade =CascadeType.PERSIST, mappedBy = "idiomas")
    private Nivel nivel;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "idiomas")
    private Asistente asistente;

    
}
