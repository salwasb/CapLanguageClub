package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Nivel;

public interface NivelDao extends JpaRepository <Nivel, Integer> {
    
}
