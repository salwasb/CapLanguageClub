package com.example.controllers;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AsistenteService;

@RestController
@RequestMapping("/asistentes")
public class AsistenteController {
    

    @Autowired
    private AsistenteService asistenteService;



 // Metodo por el cual el administrador puede eliminar un asistente, recibiendo su id
@DeleteMapping("/{id}")
public ResponseEntity<Map<String, Object>> deleteAsistente(@PathVariable(name = "id") Integer idAsistente) {
         
         ResponseEntity<Map<String, Object>> responseEntity = null;
         Map<String, Object> responseAsMap = new HashMap<>();
 
         try {
             asistenteService.deleteAsistente(asistenteService.findById(idAsistente));
             responseAsMap.put("mensaje", "L'assistant a été supprimé avec succès");
             responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
         } catch (DataAccessException e) {
             responseAsMap.put("Error Grave", "Échec de la suppression de l'assistant, en raison de : " + e.getMostSpecificCause()); 
             responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap,  
                                    HttpStatus.INTERNAL_SERVER_ERROR);                                                                  
         }
         
         return responseEntity;
 
     }






 }

