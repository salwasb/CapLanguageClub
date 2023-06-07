package com.example.service;

import java.util.List;

import com.example.entities.Asistente;

public interface AsistentesService {
    public List<Asistente> findAll();
    public Asistente findById(int idAsistente);
    public Asistente saveAsistente (Asistente asistente);
    public void deleteAsistente (Asistente asistente);
}
