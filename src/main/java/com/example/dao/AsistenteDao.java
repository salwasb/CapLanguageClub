package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Asistente;

public interface AsistenteDao extends JpaRepository<Asistente, Integer> {
  
    @Query(value = "select p from Producto p left join fetch p.presentacion")
     public List<Asistente>findAll(Sort sort);
}
