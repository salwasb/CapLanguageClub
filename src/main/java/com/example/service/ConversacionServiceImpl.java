package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.AsistenteDao;
import com.example.dao.ConversacionDao;
import com.example.entities.Asistente;
import com.example.entities.Conversacion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversacionServiceImpl implements ConversacionService{

    private final ConversacionDao conversacionDao;
   
    @Override
    public List<Conversacion> findAll() {

        return  conversacionDao.findAll();
    }

    @Override
    public Conversacion findById(int idConversacion) {
        return conversacionDao.findById(idConversacion).orElse(null);
    }

    @Override
    @Transactional
    public void delete(int idConversacion) {
        conversacionDao.deleteById(idConversacion);
    }

    @Override
    @Transactional
    public Conversacion save(Conversacion conversacion) {
       return conversacionDao.save(conversacion);
    }

    @Override
    @Transactional
    public void deleteAsistenteByIdConversacion(int idConversacion) {
       conversacionDao.deleteAsistenteByIdConversacion(idConversacion);
    }


    
}
