package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Asistente;
import com.example.service.AsistenteService;

import jakarta.validation.Valid;

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
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            responseAsMap.put("Error Grave",
                    "Échec de la suppression de l'assistant, en raison de : " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    // Metodo por el cual el administrador puede modificar un asistente, recibiendo su id
    // (creo que debe de ser casi igual que el de crear/dar de alta un asistente nuevo)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAsistente(@Valid @RequestBody Asistente asistente,
            BindingResult results,
            @PathVariable(name = "id") Integer idAsistente) {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el asistente ha llegado con errores, o si esta mal formado
        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por defecto
            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {
                mensajesError.add(objectError.getDefaultMessage());
            }

            // Hay que devolver como respuesta tres objetos
            // 1. Asistente recibido mal formado
            // 2. Mensajes de error
            // 3. Estado de la operacion (HttpStatus)
            // Por lo cual necesitamos un Map Interface, un mapa

            responseAsMap.put("asistente: ", asistente);
            responseAsMap.put("errores: ", mensajesError);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

        // Si no hay errores persistimos/guardamos el asistente y devolvemos informacion
        try {
            asistente.setId(idAsistente);
            Asistente asistenteActualizado = asistenteService.saveAsistente(asistente);

            String successMessage = "L'assistant a été mis à jour avec succès";
            responseAsMap.put("mensaje: ", successMessage);
            responseAsMap.put("asistente", asistenteActualizado);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "L'assistant n'a pas pu être mis à jour et" +
                    "la cause la plus probable de l'erreur est : " + e.getMostSpecificCause();
            responseAsMap.put("error: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
