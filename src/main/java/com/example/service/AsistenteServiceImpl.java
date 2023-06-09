package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.AsistenteDao;
import com.example.entities.Asistente;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenteServiceImpl implements AsistenteService {

    private final AsistenteDao asistenteDao;

    @Override
    public List<Asistente> findAll(Sort sort) {
       return asistenteDao.findAll(sort);
    }

    @Override
    public Page<Asistente> findAll(Pageable pageable) {
      return asistenteDao.findAll(pageable);
    
    }

    @Override
    public List<Asistente> findAll() {
      return asistenteDao.findAll();
    }

    @Override
    public Asistente findById(int idAsistente) {
      return asistenteDao.findById(idAsistente).orElse(null);
    }

    @Override
    public Asistente saveAsistente(Asistente asistente) {
       return asistenteDao.save(asistente);
    }

    @Override
    @Transactional
    public void deleteAsistente(Asistente asistente) {
      asistenteDao.delete(asistente);
    }

    @Override
    public Asistente updateAsistente(Asistente asistente) {
      return asistenteDao.save(asistente);
      
    }

    



  
    
}
