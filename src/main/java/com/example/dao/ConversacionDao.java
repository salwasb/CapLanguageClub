package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Conversacion;

public interface ConversacionDao extends JpaRepository<Conversacion, Integer> {

     @Modifying
     @Query(value = "delete from Asistente a where a.id = :idAsistente")
     public void deleteAsistenteByIdConversacion(int idAsistente);
    
    
}
