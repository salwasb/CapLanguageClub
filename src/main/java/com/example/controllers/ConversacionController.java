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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Conversacion;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/conversations")
public class ConversacionController {

    @Autowired
    ConversacionService conversacionService;

    @Autowired
    AsistenteService asistenteService;

    @GetMapping
    public ResponseEntity<List<Conversacion>> findAll() {

        List<Conversacion> conversaciones = new ArrayList<>();

        ResponseEntity<List<Conversacion>> responseEntity = null;

        // Devolver los productos ordenados
        try {
            conversaciones = conversacionService.findAll();
            responseEntity = new ResponseEntity<List<Conversacion>>(conversaciones, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    // Metodo que persiste una conversacion en la base de datos
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveConversation(@Valid @RequestBody Conversacion conversacion,
            BindingResult results) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el producto ha llegado con errores, es decir si está mal formado
        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto
            for (ObjectError objectError : results.getAllErrors()) {
                mensajesError.add(objectError.getDefaultMessage());
            }
            responseAsMap.put("Erreurs: ", mensajesError);
            responseAsMap.put("Conversation: ", conversacion);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        // Si no hay errores persistimos la conversacion y devolvemos informacion

        try {
            Conversacion conversacionPersistida = conversacionService.save(conversacion);
            String sucessMessage = "La conversation a été créé avec succès";
            responseAsMap.put("Message: ", sucessMessage);
            responseAsMap.put("Conversation", conversacionPersistida);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "Le produit n'a pas pu être créé et la causa la plus probable de l'erreur est: "
                    + e.getMostSpecificCause();

            responseAsMap.put("Erreur: ", errorMessage);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    // Metodo para recuperar un producto por el id.

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByIdProducto(
            @PathVariable(name = "id", required = true) Integer idConversacion) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            Conversacion conversacion = conversacionService.findById(idConversacion);

            if (conversacion != null) {
                String successMessage = "La conversation avec ID: " + idConversacion + " a été trouvée.";
                responseAsMap.put("Message", successMessage);
                responseAsMap.put("Conversation", conversacion);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String notFoundMessage = "La conversation avec ID:  " + idConversacion + " n'a pas pu être trouvée.";
                responseAsMap.put("Message", notFoundMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            String errorMessage = "Erreur grave, et la causa la plus probable de l'erreur est: "
                    + e.getMostSpecificCause();
            responseAsMap.put("Erreur Grave", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // Metodo que actualiza una conversacion dado el id de la misma
    // Es basicamente igual al de persistir una conversacion nueva
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateConversation(@Valid @RequestBody Conversacion conversacion,
            BindingResult results,
            @PathVariable(name = "id") Integer idConversacion) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el producto ha llegado con errores, es decir si está mal formado
        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto
            for (ObjectError objectError : results.getAllErrors()) {
                mensajesError.add(objectError.getDefaultMessage());
            }

            responseAsMap.put("Erreur: ", mensajesError);
            responseAsMap.put("Conversation: ", conversacion);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        // Si no hay errores persistimos el producto y devolvemos informacion

        try {
            conversacion.setId(idConversacion);
            Conversacion conversacionActualizada = conversacionService.save(conversacion);
            String sucessMessage = "La conversation a été mis à jour correctement";
            responseAsMap.put("Mensaje: ", sucessMessage);
            responseAsMap.put("Conversation", conversacionActualizada);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "La conversation n'a pas pu être mis à jour et la causa la plus probable de l'erreur est: "
                    + e.getMostSpecificCause();

            responseAsMap.put("Erreur: ", errorMessage);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // Metodo para eliminar una conversacion cuyo id se recibe como parametro de la
    // peticion
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteConversacion(@RequestParam(name = "idAsistente") Integer idAsistente,
            @PathVariable(name = "id") Integer idConversacion) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            asistenteService.deleteAsistenteById(idAsistente);
            conversacionService.delete(idConversacion);

            responseAsMap.put("Message", "La conversation a été supprimée avec succès");
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        
        } catch (DataAccessException e) {
            responseAsMap.put("Erreur Grave",
                    "La conversation n'a pas pus être supprimée, a cause de: " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
