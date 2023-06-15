package com.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.model.FileUploadResponse;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/assistants")
public class AsistenteController {

    @Autowired
    private AsistenteService asistenteService;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private FileDownloadUtil fileDownloadUtil;
    @Autowired
    private ConversacionService conversacionService;

    @GetMapping
    public ResponseEntity<List<Asistente>> findAllByPage(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        List<Asistente> asistentes = new ArrayList<>();
        Sort sortByLastName = Sort.by("apellidos");
        ResponseEntity<List<Asistente>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        // Primero comprobar si se requiere paginacion, o no
        if (page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortByLastName);

            // Se solicita el listado de asistentes pagignados.
            try {
                Page<Asistente> asistentesPaginados = asistenteService.findAll(pageable);
                asistentes = asistentesPaginados.getContent();
                String successMessage = "Les assistants son trouvés.";
                responseAsMap.put("Message: ", successMessage);
                responseEntity = new ResponseEntity<List<Asistente>>(asistentes, HttpStatus.OK);

            } catch (Exception e) {
                String failedMessage = "Les assistants ne son pas trouvés.";
                responseAsMap.put("Message: ", failedMessage);
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // Devolver los asistentes ordenados
            try {
                asistentes = asistenteService.findAll(sortByLastName);
                responseEntity = new ResponseEntity<List<Asistente>>(asistentes, HttpStatus.OK);

                ResponseEntity<List<Conversacion>> responseEntity2;
                Asistente asistente = new Asistente();

                List<Conversacion> conversaciones = asistente.getConversacion();
                responseEntity2 = new ResponseEntity<List<Conversacion>>(conversaciones, HttpStatus.OK);

            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return responseEntity;
    }

    // Metodo por el cual se obtienen TODOS los asistentes, SIN paginación
    // @GetMapping
    // public ResponseEntity<List<Asistente>> findAll() {

    // List<Asistente> asistentes = new ArrayList<>();

    // ResponseEntity<List<Asistente>> responseEntity = null;

    // asistentes = asistenteService.findAll();

    // responseEntity = new ResponseEntity<List<Asistente>>(asistentes,
    // HttpStatus.OK);

    // return responseEntity;
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findAsistenteById(
            @PathVariable(name = "id", required = true) Integer idAsistente) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            Asistente asistente = asistenteService.findById(idAsistente);

            if (asistente != null) {
                String successMessage = "L'assistant avec ID: " + idAsistente + " a été trouvé.";
                responseAsMap.put("Message: ", successMessage);
                responseAsMap.put("Assistant: ", asistente);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String notFoundMessage = "L'assistant avec ID:  " + idAsistente + " n'a pas pu être trouvé.";
                responseAsMap.put("Message: ", notFoundMessage);
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

    @PostMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveAsistente(
            @Valid @RequestPart(name = "asistente") Asistente asistente,
            BindingResult results,
            @RequestPart(name = "file") MultipartFile file) throws IOException {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {

                mensajesError.add(objectError.getDefaultMessage());
            }
            responseAsMap.put("Erreur: ", mensajesError);
            responseAsMap.put("Assistant: ", asistente);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        if (!file.isEmpty()) {

            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
            asistente.setImagenAsistente(fileCode + "-" + file.getOriginalFilename());

            FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
                    .fileName(fileCode + "-" + file.getOriginalFilename())
                    .downloadURI("/asistentes/downloadFile/"
                            + fileCode + "-" + file.getOriginalFilename())
                    .size(file.getSize())
                    .build();

            responseAsMap.put("Information de l'image: ", fileUploadResponse);
        }

        try {
            Asistente asistentePersistido = asistenteService.saveAsistente(asistente);
            String successMessage = "L'assistant a été créé correctement.";
            responseAsMap.put("Message: ", successMessage);
            responseAsMap.put("Asistant: ", asistentePersistido);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "L'assistant n'a pas pu être créé et la cause la plus probable de l'erreur est: "
                    + e.getMostSpecificCause();
            responseAsMap.put("Erreur: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> updateAsistente(
            @Valid @RequestPart(name = "asistente") Asistente asistente,
            @PathVariable(name = "id") Integer idAsistente,
            BindingResult results,
            @RequestPart(name = "file") MultipartFile file) throws IOException {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {
                mensajesError.add(objectError.getDefaultMessage());
            }
            responseAsMap.put("Assistant: ", asistente);
            responseAsMap.put("Erreur: ", mensajesError);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        try {
            asistente.setId(idAsistente);
            Asistente asistenteActualizado = asistenteService.saveAsistente(asistente);

            String successMessage = "L'assistant a été mis à jour avec succès.";
            responseAsMap.put("Message: ", successMessage);
            responseAsMap.put("Assistant: ", asistenteActualizado);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "L'assistant n'a pu être mis à jour et la cause la plus probable est : "
                    + e.getMostSpecificCause();
            responseAsMap.put("Erreur: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!file.isEmpty())

        {
            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
            asistente.setImagenAsistente(fileCode + "-" + file.getOriginalFilename());

            // Devolver informacion respecto al file recibido
            FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
                    .fileName(fileCode + "-" + file.getOriginalFilename())
                    .downloadURI("/asistentes/downloadFile/"
                            + fileCode + "-" + file.getOriginalFilename())
                    .size(file.getSize())
                    .build();

            responseAsMap.put("Information de l'image: ", fileUploadResponse);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAsistente(@PathVariable(name = "id") Integer idAsistente) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            asistenteService.deleteAsistente(asistenteService.findById(idAsistente));
            responseAsMap.put("Message", "L'assistant a été supprimé avec succès.");
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            responseAsMap.put("Erreur grave: ",
                    "Échec de la suppression de l'assistant et la cause est: " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "fileCode") String fileCode) {

        Resource resource = null;

        try {
            resource = fileDownloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File ne pas trouvé. ", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<Map<String, Object>> findConversacionesById(
            @PathVariable(name = "id", required = true) Integer idAsistente) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();
        Asistente asistente = asistenteService.findById(idAsistente);

        LocalDate hoy = LocalDate.now();

        try {
            List<Conversacion> conversaciones = conversacionService.findAll();
            List<Conversacion> conv = conversaciones.stream()
                    .filter(c -> c.getFecha().isAfter(hoy) && c.getIdioma().equals(asistente.getIdioma())
                            && c.getNivel().equals(asistente.getNivel()))
                    .collect(Collectors.toList());

            if (conv != null) {
                String successMessage = "Les conversations de l'assistant avec ID: " + idAsistente
                        + " ont été trouvés.";
                responseAsMap.put("Message: ", successMessage);
                responseAsMap.put("Conversations: ", conv);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String notFoundMessage = "Les conversations de l'assistant avec ID:  " + idAsistente
                        + " ne sont pas pu être trouvés.";
                responseAsMap.put("Message: ", notFoundMessage);
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

}