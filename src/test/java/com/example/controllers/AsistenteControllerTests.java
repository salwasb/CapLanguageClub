package com.example.controllers;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.entities.Idioma;
import com.example.entities.Modo;
import com.example.entities.Nivel;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc 
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class AsistenteControllerTests {

        @Autowired
        private MockMvc mockMvc; 

        @MockBean
        private AsistenteService asistenteService;

        @MockBean
        private ConversacionService conversacionService;

        @InjectMocks
        private AsistenteController asistenteController;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private FileUploadUtil fileUploadUtil;

        @MockBean
        private FileDownloadUtil fileDownloadUtil;

        @Autowired
        private WebApplicationContext context;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(springSecurity())
                                .build();
        }

    @Test
    @DisplayName("test guardar asistente sin autorizacion")
    void testGuardarAsistente() throws Exception {

        List<Conversacion> conversacion1 = new ArrayList<>();

        Conversacion conversacion = Conversacion.builder()
                                .titulo("francais")
                                .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                                .hora(LocalTime.of(20, 30, 20))
                                .modo(Modo.ONLINE)
                                .lugar("Planta 2")
                                .idioma(Idioma.FRANCES)
                                .nivel(Nivel.A)
                                .numeroAsistentes(4)
                                .build();
        conversacion1.add(conversacion);

        Asistente asistente = Asistente.builder()
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .conversacion(conversacion1)
                .build();

        String jsonStringAsistente = objectMapper.writeValueAsString(asistente);

        MockMultipartFile bytesArrayAsistente = new MockMultipartFile("asistente",
                null, "application/json", jsonStringAsistente.getBytes());
                
        mockMvc.perform(multipart("/asistentes")
                .file("file", null)
                .file(bytesArrayAsistente))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Test guardar asistente con usuario mockeado")
    @Test
    @WithMockUser(username = "yaku@cap.com", authorities = { "ADMIN" })
    void testGuardarAsistenteConUserMocked() throws Exception {
       
        List<Conversacion> conversacion1 = new ArrayList<>();

        Conversacion conversacion = Conversacion.builder()
                .titulo("Anglais ")
                .fecha(LocalDate.of(2023, Month.DECEMBER, 11))
                .hora(LocalTime.of(20, 30, 20))
                .modo(Modo.ONLINE)
                .lugar("Planta 2")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .numeroAsistentes(4)
                .build();
        conversacion1.add(conversacion);

        Asistente asistente = Asistente.builder()
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .conversacion(conversacion1)
                .build();
        
        given(asistenteService.saveAsistente(any(Asistente.class)))
        .willAnswer(invocation -> invocation.getArgument(0));
      
        String jsonStringAsistente = objectMapper.writeValueAsString(asistente);

        MockMultipartFile bytesArrayAsistente = new MockMultipartFile("asistente",
                null, "application/json", jsonStringAsistente.getBytes());

        ResultActions response = mockMvc.perform(multipart("/assistants")
                .file("file", null)
                .file(bytesArrayAsistente));
     

        response.andDo(print())
                .andExpect(status().isCreated());
//                 .andExpect(jsonPath("$.asistente.nombre", is(asistente.getNombre())))
//                 .andExpect(jsonPath("$.asistente.apellidos", is(asistente.getApellidos())));
    }

    @Test
    @WithMockUser(username = "salwa@cap.com", authorities = { "ADMIN" })
    public void testRecuperarAsistentePorID() throws Exception {

        int asistenteId = 1;

        List<Conversacion> conversacion1 = new ArrayList<>();
        List<Asistente> asistentes = new ArrayList<>();

        Conversacion conversacion = Conversacion.builder()
                .titulo("Anglais ")
                .fecha(LocalDate.of(2023, Month.DECEMBER, 11))
                .hora(LocalTime.of(20, 30, 20))
                .modo(Modo.ONLINE)
                .lugar("Planta 2")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .asistentes(asistentes)
                .build();
        conversacion1.add(conversacion);

        Asistente asistente = Asistente.builder()
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .conversacion(conversacion1)
                .build();
        asistentes.add(asistente);

        given(asistenteService.findById(asistenteId)).willReturn(asistente);

        ResultActions response = mockMvc.perform(get("/assistants/{id}", asistenteId));

        response.andExpect(status().isOk())
                .andDo(print());
                // .andExpect(jsonPath("$.asistente.nombre", is(asistente.getNombre())));
    }

    @Test
    @WithMockUser(username = "vrmachado@cap.com", authorities = { "ADMIN" }) 
    public void testAsistenteNoEncontrado() throws Exception {

        int asistenteId = 1;

        given(asistenteService.findById(asistenteId)).willReturn(null);

        ResultActions response = mockMvc.perform(get("/assistants/{id}", asistenteId));

        response.andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "vrmachado@cap.com", authorities = { "ADMIN" }) 
    public void testEliminarAsistente() throws Exception {

        List<Conversacion> conversacion1 = new ArrayList<>();

        Conversacion conversacion = Conversacion.builder()
                .titulo("Anglais ")
                .fecha(LocalDate.of(2023, Month.DECEMBER, 11))
                .hora(LocalTime.of(20, 30, 20))
                .modo(Modo.ONLINE)
                .lugar("Planta 2")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .build();
        conversacion1.add(conversacion);

        Asistente asistente = Asistente.builder()
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .conversacion(conversacion1)
                .build();

        asistenteService.saveAsistente(asistente);

        given(asistenteService.findById(asistente.getId()))
                .willReturn(asistente);

        willDoNothing().given(asistenteService).deleteAsistente(asistente);

        ResultActions response = mockMvc.perform(delete("/assistants/{id}", asistente.getId()));

        response.andExpect(status().isOk())
                .andDo(print());

    }

}
