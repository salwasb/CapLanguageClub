package com.example.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.dao.AsistenteDao;
import com.example.dao.ConversacionDao;
import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.entities.Idioma;
import com.example.entities.Modo;
import com.example.entities.Nivel;
import com.example.service.AsistenteServiceImpl;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AsistenteServiceTests {

    @Mock
    private AsistenteDao asistenteDao;

    @Mock
    private ConversacionDao conversacionDao;

    @InjectMocks
    private AsistenteServiceImpl asistenteServiceImpl;

    private Asistente asistente;

    @BeforeEach
    void setUp() {
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

        asistente = Asistente.builder()
                .nombre("Nawal")
                .apellidos("AAA")
                .imagenAsistente("test.jpeg")
                .correo("jgjfj@cap.com")
                .idioma(Idioma.INGLES)
                .nivel(Nivel.A)
                .conversacion(conversacion1)
                .build();

    }

    @Test
    @DisplayName("test de pesistir asistente")
    public void testGuardarAsistente() {

        given(asistenteDao.save(asistente)).willReturn(asistente);

        Asistente asistenteGuardado = asistenteServiceImpl.saveAsistente(asistente);

        assertThat(asistenteGuardado).isNotNull();
    }

    @Test
    @DisplayName("Recuperar una lista de asistentes vacía")
    public void testEmptyAsistenteList() {

        given(asistenteDao.findAll()).willReturn(Collections.emptyList());

        List<Asistente> asistentes = asistenteDao.findAll();

        assertThat(asistentes).isEmpty();

    }

    @Test
    @DisplayName("Eliminar asistente")
    public void testEliminarAsistente() {

        asistenteDao.delete(asistente);

        Optional<Asistente> optionalAsistente = asistenteDao.findById(asistente.getId());

        assertThat(optionalAsistente).isEmpty();

    }

    @Test
    @DisplayName("Test Actualizar Asistente")
    public void ActualizarAsistente() {

        given(asistenteDao.save(asistente)).willReturn(asistente);

        Asistente asistenteGuardado = asistenteServiceImpl.saveAsistente(asistente);

        asistenteGuardado.setApellidos("NNNN");
        asistenteGuardado.setNombre("LLLL");

        Asistente asistenteActualizado = asistenteDao.save(asistenteGuardado);

        assertThat(asistenteActualizado.getApellidos()).isEqualTo("NNNN");
        assertThat(asistenteActualizado.getNombre()).isEqualTo("LLLL");

    }
}
