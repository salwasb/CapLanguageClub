package com.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.dto;
import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.model.FileUploadResponse;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.utilities.FileUploadUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/conversations")
public class ConversacionController {

    @Autowired
    ConversacionService conversacionService;

    @Autowired
    AsistenteService asistenteService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @GetMapping
    public ResponseEntity<List<List<Conversacion>>> findAll() {

        List<Conversacion> conversaciones = new ArrayList<>();
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<List<List<Conversacion>>> responseEntity = null;

        try {
            conversaciones = conversacionService.findAll();
            if (conversaciones != null) {
                String successMessage = "Les conversations son trouvés.";
                responseAsMap.put("Message: ", successMessage);
                responseEntity = new ResponseEntity<>(Collections.singletonList(conversaciones), HttpStatus.OK);

                ResponseEntity<List<Asistente>> responseEntity2;
                Conversacion conversacion = new Conversacion();

                List<Asistente> asistentes = conversacion.getAsistentes();
                responseEntity2 = new ResponseEntity<List<Asistente>>(asistentes,
                        HttpStatus.OK);
            }else{
                String failedMessage = "Les conversations ne son pas trouvés.";
                responseAsMap.put("Message: ", failedMessage);
                responseEntity =  new ResponseEntity<>(Collections.singletonList(conversaciones), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {

            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveConversation(@Valid @RequestBody Conversacion conversacion,
            BindingResult results) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            for (ObjectError objectError : results.getAllErrors()) {
                mensajesError.add(objectError.getDefaultMessage());
            }
            responseAsMap.put("Erreurs: ", mensajesError);
            responseAsMap.put("Conversation: ", conversacion);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        try {
            Conversacion conversacionPersistida = conversacionService.save(conversacion);
            String sucessMessage = "La conversation a été créée avec succès";
            responseAsMap.put("Message: ", sucessMessage);
            responseAsMap.put("Conversation: ", conversacionPersistida);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "La conversation n'a pas pu être créée et la cause la plus probable est: "
                    + e.getMostSpecificCause();

            responseAsMap.put("Erreur: ", errorMessage);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findByIdCOnversacion(
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
            String errorMessage = "Erreur grave et la cause la plus probable est: "
                    + e.getMostSpecificCause();
            responseAsMap.put("Erreur: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conversacion> updateConversacion(
            @PathVariable int id,
            @RequestBody dto dto) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        Conversacion conversacionExistente = conversacionService.findById(id);

        if (conversacionExistente == null) {
            String notFoundMessage = "La conversation avec ID: " + id + " n'a pas pu être trouvée.";
            responseAsMap.put("Erreur: ", notFoundMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        } else {

            BeanUtils.copyProperties(dto, conversacionExistente, "nivel", "idioma");
            Conversacion conversacionActualizada = conversacionService.save(conversacionExistente);
            String successMessage = "La conversation avec ID: " + id + " a été trouvée.";
            responseAsMap.put("Message: ", successMessage);
            responseAsMap.put("Conversation: ", conversacionActualizada);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            return ResponseEntity.ok(conversacionActualizada);
        }

    }

    // @PutMapping("/conversation/{id}")
    // public ResponseEntity<Map<String, Object>> updateConversation(@Valid
    // @RequestBody Conversacion conversacion,
    // BindingResult results,
    // @PathVariable(name = "id") Integer idConversacion) {

    // Map<String, Object> responseAsMap = new HashMap<>();
    // ResponseEntity<Map<String, Object>> responseEntity = null;

    // // Comprobar si el producto ha llegado con errores, es decir si está mal

    // if (results.hasErrors()) {

    // List<String> mensajesError = new ArrayList<>();

    // // Quiero recorrer los resultados de la validacion y extraer los mensajes por
    // // defecto
    // for (ObjectError objectError : results.getAllErrors()) {
    // mensajesError.add(objectError.getDefaultMessage());
    // }
    // String sucessMessage = "Le niveau et le langue ne peut mis à jour pas";
    // responseAsMap.put("Erreur: ", mensajesError);
    // responseAsMap.put("Conversation: ", conversacion);
    // responseAsMap.put("Niveau et langue", sucessMessage);

    // responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
    // HttpStatus.BAD_REQUEST);

    // return responseEntity;
    // }
    // try {
    // conversacion.setId(idConversacion);
    // Conversacion conversacionActualizada =
    // conversacionService.save(conversacion);
    // String sucessMessage = "La conversation a été mis à jour correctement";
    // responseAsMap.put("Mensaje: ", sucessMessage);
    // responseAsMap.put("Conversation", conversacionActualizada);
    // responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
    // HttpStatus.OK);
    // } catch (DataAccessException e) {
    // String errorMessage = "La conversation n'a pas pu être mis à jour et la causa
    // la plus probable de l'erreur est: "
    // + e.getMostSpecificCause();

    // responseAsMap.put("Erreur: ", errorMessage);

    // responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // return responseEntity;
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteConversacion(
            @PathVariable(name = "id") Integer idConversacion) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();
        Conversacion conversacion = conversacionService.findById(idConversacion);

        try {
            if (idConversacion != null) {

                conversacion.getAsistentes().clear();

                conversacionService.deleteConversacionById(idConversacion);

                conversacionService.delete(idConversacion);

                responseAsMap.put("Message", "La conversation a été supprimée avec succès");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            }
        } catch (DataAccessException e) {
            responseAsMap.put("Erreur Grave",
                    "La conversation n'a pas pu être supprimée et la cause la plus probable: "
                            + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/addAsistentes")
    @Transactional
    public ResponseEntity<Map<String, Object>> addAsistenteAConver(
            @Valid @RequestParam(name = "idAsistente") Integer idAsistente,
            @RequestParam(name = "idConversacion") Integer idConversacion)
           throws IOException {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();
        Asistente asistente = asistenteService.findById(idAsistente);

        Conversacion conversacion = conversacionService.findById(idConversacion);
        LocalDate diaConversacion = conversacion.getFecha();
        if (asistente.getConversacion() != null && asistente.getConversacion().size() != 0) {

            List<Integer> diasDistintos = asistente.getConversacion().stream()
                    .filter(c -> !c.getFecha().equals(diaConversacion))
                    .map(c -> c.getFecha().getDayOfMonth()).toList();
                 
            if (diasDistintos != null && diasDistintos.size() != 0) {
                
                if (conversacion.getNumeroAsistentes() < 8) {

                    conversacion.getAsistentes().add(asistenteService.findById(idAsistente));

                    List<Conversacion> listaDeConversacion = new ArrayList<>();
                    listaDeConversacion.add(conversacion);
                    asistente.setConversacion(listaDeConversacion);

                    List<Asistente> asistentePersistido = conversacion.getAsistentes();
                    conversacion.setNumeroAsistentes(conversacion.getAsistentes().size());
                    responseAsMap.put("Message: ", "L'assistant á été ajouté á la conversation.");
                    responseAsMap.put("Asistents: ", asistentePersistido);
                    responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                } else{

                    String errorMessage = "Le liste d'assistants est complet";
                    responseAsMap.put("Erreur: ", errorMessage);
                    responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
                }
            } else {

                String errorMessage = "Vous êtes déjà dans une conversation le même jour.";
                responseAsMap.put("Erreur: ", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            }

        } else {
            if (conversacion.getAsistentes().size() <= 8) {
                conversacion.getAsistentes().add(asistenteService.findById(idAsistente));

                List<Conversacion> listaDeConversacion = new ArrayList<>();
                listaDeConversacion.add(conversacion);
                asistente.setConversacion(listaDeConversacion);

                List<Asistente> asistentePersistido = conversacion.getAsistentes();
                conversacion.setNumeroAsistentes(conversacion.getAsistentes().size());
                responseAsMap.put("Message: ", "L'assistant a été ajouté à la conversation.");
                responseAsMap.put("Asistentes: ", asistentePersistido);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {

                String errorMessage = "La liste d'assistants est complet";
                responseAsMap.put("Erreur: ", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            }
        }
        return responseEntity;
    }
}