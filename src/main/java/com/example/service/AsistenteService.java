package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Asistente;
public interface AsistenteService {

    public List<Asistente> findAll(Sort sort);
    public Page<Asistente> findAll(Pageable pageable);
    public List<Asistente> findAll();
    public Asistente findById(int idAsistente);
    public Asistente saveAsistente (Asistente asistente);
    public void deleteAsistente (Asistente asistente);
    public Asistente updateAsistente (Asistente asistente);
    public void deleteAsistenteById (int idAsistente);
}
