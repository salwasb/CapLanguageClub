package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Conversacion;

public interface ConversacionDao extends JpaRepository<Conversacion, Integer> {
    
    
}
