package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Conversacion;

public interface ConversacionDao extends JpaRepository<Conversacion, Integer> {

     @Modifying
     @Query(value = "delete from asistentes_conversacion where asistente_id = ?1", nativeQuery = true)
     public void deleteAsistenteByIdConversacion(int idAsistente);

     @Modifying
     @Query(value = "DELETE FROM asistentes_conversacion \n" + //
               "WHERE\n" + //
               "    conversacion_id = ?1", nativeQuery = true)
     public void deleteConversacionById(int id);

     @Modifying
     @Query(value = "delete from conversaciones where id=?", nativeQuery = true)
     public void deleteConversacion(Conversacion conversacion);

     
   

 
    
    
}
