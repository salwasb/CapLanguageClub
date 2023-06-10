package com.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Asistente;
import com.example.model.FileUploadResponse;
import com.example.service.AsistenteService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/asistentes")
@RequiredArgsConstructor
public class AsistenteController {

    @Autowired
    private AsistenteService asistenteService;

    private final FileUploadUtil fileUploadUtil;

    private final FileDownloadUtil fileDownloadUtil;

    // Metodo por el cual se obtienen TODOS los asistentes, con paginación

    @GetMapping
    public ResponseEntity<List<Asistente>> findAllByPage(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        List<Asistente> asistentes = new ArrayList<>();
        Sort sortByLastName = Sort.by("apellidos");
        ResponseEntity<List<Asistente>> responseEntity = null;

        // Primero comprobar si se requiere paginacion, o no
        if (page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortByLastName);

            // Se solicita el listado de asistentes pagignados.
            try {
                Page<Asistente> asistentesPaginados = asistenteService.findAll(pageable);
                asistentes = asistentesPaginados.getContent();
                responseEntity = new ResponseEntity<List<Asistente>>(asistentes,
                        HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // Devolver los asistentes ordenados
            try {
                asistentes = asistenteService.findAll(sortByLastName);
                responseEntity = new ResponseEntity<List<Asistente>>(asistentes, HttpStatus.OK);
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

    // //Método por el cual el administrador puede obtener un asistente por su id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findAsistenteById(
            @PathVariable(name = "id", required = true) Integer idAsistente) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            Asistente asistente = asistenteService.findById(idAsistente);

            if (asistente != null) {
                String successMessage = "L'assistant avec ID: " + idAsistente + " a été trouvée.";
                responseAsMap.put("Message", successMessage);
                responseAsMap.put("Asistant", asistente);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String notFoundMessage = "L'assistant avec ID:  " + idAsistente + " n'a pas pu être trouvée.";
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

    // Metodo que persiste un asistente en la base de datos
    @PostMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveAsistente(
            @Valid @RequestPart(name = "asistente") Asistente asistente,
            BindingResult results,
            @RequestPart(name = "file") MultipartFile file) throws IOException {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // comprobar si el asistente a llegado con errores, es decir, si esta mal
        // formado
        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto
            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {

                mensajesError.add(objectError.getDefaultMessage());
            }
            responseAsMap.put("Erreur: ", mensajesError);
            responseAsMap.put("asistente: ", asistente);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        // Si no hay errores, entonces persistimos el asistente,
        // comprobando previamente si nos han enviado una imagen
        // , o un archivo.
        if (!file.isEmpty()) {
            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
            asistente.setImagenAsistente(fileCode + "-" + file.getOriginalFilename());

            // Devolver informacion respecto al file recibido
            FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
                    .fileName(fileCode + "-" + file.getOriginalFilename())
                    .downloadURI("/asistentes/downloadFile/"
                            + fileCode + "-" + file.getOriginalFilename())
                    .size(file.getSize())
                    .build();

            responseAsMap.put("Informations sur l'image: ", fileUploadResponse);

        }

        try {
            Asistente asistentePersistido = asistenteService.saveAsistente(asistente);
            String successMessage = "L'assistant a été créé avec succès";
            responseAsMap.put("Mensage: ", successMessage);
            responseAsMap.put("Asistant:", asistentePersistido);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "L'assistant n'a pas pu être conservé et la cause la plus probable de l'erreur est: "
                    + e.getMostSpecificCause();
            responseAsMap.put("Erreur", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // Metodo por el cual el administrador puede modificar un asistente, recibiendo
    // su id
    // (creo que debe de ser casi igual que el de crear/dar de alta un asistente
    // nuevo)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAsistente(@Valid 
            @RequestPart(name = "asistente") Asistente asistente,
            BindingResult results,
            @PathVariable(name = "id") Integer idAsistente,
            @RequestPart(name = "file") MultipartFile file) throws IOException{

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Comprobar si el asistente ha llegado con errores, o si esta mal formado
        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();

            // Quiero recorrer los resultados de la validacion y extraer los mensajes por
            // defecto
            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {
                mensajesError.add(objectError.getDefaultMessage());
            }

            responseAsMap.put("Asistant: ", asistente);
            responseAsMap.put("Erreur: ", mensajesError);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        // Si no hay errores persistimos/guardamos el asistente y devolvemos informacion
        try {
            asistente.setId(idAsistente);
            Asistente asistenteActualizado = asistenteService.saveAsistente(asistente);

            String successMessage = "L'assistant a été créé avec succès";
            responseAsMap.put("Mensage: ", successMessage);
            responseAsMap.put("Asistant", asistenteActualizado);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "L'assistant n'a pas pu être mis à jour et" +
                    "la cause la plus probable de l'erreur est : " + e.getMostSpecificCause();
            responseAsMap.put("Erreur: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // Metodo por el cual el administrador puede eliminar un asistente, recibiendo
    // su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAsistente(@PathVariable(name = "id") Integer idAsistente) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {
            asistenteService.deleteAsistente(asistenteService.findById(idAsistente));
            responseAsMap.put("Mensage", "L'assistant a été supprimé avec succès");
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            responseAsMap.put("Erreur Grave",
                    "Échec de la suppression de l'assistant, en raison de : " + e.getMostSpecificCause());
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
            return new ResponseEntity<>("File not found ", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
        .body(resource);
    }

}