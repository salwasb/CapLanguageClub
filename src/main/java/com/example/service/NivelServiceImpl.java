package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.NivelDao;
import com.example.entities.Nivel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl implements NivelService {

private final NivelDao nivelDao;

    @Override
    public List<Nivel> findAll() {
        return nivelDao.findAll();
    }

    @Override
    public Nivel findById(int idNivel) {
        return nivelDao.findById(idNivel).get();
    }

    @Override
    public Nivel save(Nivel nivel) {
       return nivelDao.save(nivel);
    }
    
}
