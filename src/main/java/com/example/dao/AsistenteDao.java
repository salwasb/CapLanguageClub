package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Asistente;

public interface AsistenteDao extends JpaRepository<Asistente, Integer> {
    
}
