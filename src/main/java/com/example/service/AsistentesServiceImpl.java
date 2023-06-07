package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.AsistenteDao;
import com.example.entities.Asistente;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistentesServiceImpl implements AsistentesService {

    private final AsistenteDao asistenteDao;

    @Override
    public List<Asistente> findAll() {
       return asistenteDao.findAll();
    }

    @Override
    public Asistente findById(int idAsistente) {
      return asistenteDao.findById(idAsistente).get();
    }

    @Override
    @Transactional
    public Asistente saveAsistente(Asistente asistente) {
       return asistenteDao.save(asistente);
    }

    @Override
    @Transactional
    public void deleteAsistente(Asistente asistente) {
      asistenteDao.delete(asistente);
    }
    
}
