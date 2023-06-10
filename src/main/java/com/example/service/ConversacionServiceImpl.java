package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ConversacionDao;
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
    public Page<Conversacion> findAll(Pageable pageable) {
        return conversacionDao.findAll(pageable); 
    
    }

    @Override
    public List<Conversacion> findAll(Sort sort) {
    
        return conversacionDao.findAll(sort); 
    }

    
}

