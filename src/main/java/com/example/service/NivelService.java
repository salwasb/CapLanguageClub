package com.example.service;

import java.util.List;

import com.example.entities.Nivel;

public interface NivelService {
    
    public List<Nivel> findAll();

    public Nivel findById(int id);

}

