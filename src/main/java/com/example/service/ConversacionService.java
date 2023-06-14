package com.example.service;

import java.util.List;

import com.example.entities.Conversacion;

public interface ConversacionService {
    public List<Conversacion> findAll(); 
    public Conversacion findById(int idConversacion);
    public void delete(int idConversacion);
    public Conversacion save (Conversacion conversacion); 
    public void deleteAsistenteByIdConversacion(int idConversacion);
     public void deleteConversacionById(int id);

}
