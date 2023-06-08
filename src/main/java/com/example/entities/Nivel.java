package com.example.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "niveles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nivel implements Serializable {

    private static final long  serialVersionUID =1L;
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "niveles")
    @JsonIgnore
    private List<Asistente> asistentes;

    @OneToOne(fetch = FetchType.LAZY)
   // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Conversacion conversacion;

    
    


}
