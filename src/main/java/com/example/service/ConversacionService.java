package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Conversacion;

public interface ConversacionService {
    public Page<Conversacion> findAll(Pageable pageable); 
    public List<Conversacion> findAll(Sort sort);
    public List<Conversacion> findAll(); 
    public Conversacion findById(int idConversacion);
    public void delete(int idConversacion);
    public Conversacion save (Conversacion conversacion); 
   
}
