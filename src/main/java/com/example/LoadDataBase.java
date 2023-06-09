package com.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Asistente;
import com.example.entities.Conversacion;
import com.example.entities.Idioma;
import com.example.entities.Modo;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.service.NivelService;

@Configuration
public class LoadDataBase {

    @Bean

    public CommandLineRunner sampleData(ConversacionService conversacionService, AsistenteService asistentesService,
            NivelService nivelService) {

                // List<Asistente> asistentes1 = new ArrayList<>();
                // List<Asistente> asistentes2 = new ArrayList<>();

                // Asistente asis1 = Asistente.builder()
                // .apellidos("DDD")
                // .build();
                

        return args -> {

            conversacionService.save(
                    Conversacion.builder()
                            .id(1)
                            .titulo("francais")
                            .fecha(LocalDate.of(2023, 12, 23))
                            .hora(LocalTime.of(10, 15))
                            .modo(Modo.ONLINE)
                            .lugar("Planta 2")
                            .idioma(Idioma.FRANCES)
                            .nivel(nivelService.findById(1))
                            .build()
            );
            conversacionService.save(
                    Conversacion.builder()
                            .id(2)
                            .titulo("anglais")
                            .fecha(LocalDate.of(2023, 12, 20))
                            .hora(LocalTime.of(10, 15))
                            .modo(Modo.PRESENCIAL)
                            .lugar("Planta 2")
                            .idioma(Idioma.INGLES)
                            .nivel(nivelService.findById(2))
                            .build()
            );

            asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(1)
                            .nombre("Nawal")
                            .apellidos("AAA")
                            .correo("jgjfj")
                            .idioma(Idioma.FRANCES)
                            .niveles(nivelService.findById(1))
                            .conversacion(conversacionService.findById(1))
                            .build()

            );
            asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(2)
                            .nombre("Mimi")
                            .apellidos("BBB")
                            .correo("kffkkf")
                            .idioma(Idioma.INGLES)
                            .niveles(nivelService.findById(2))
                            .conversacion(conversacionService.findById(2))
                            .build()
            );

            asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(3)
                            .nombre("Richi")
                            .apellidos("CCC")
                            .correo("kdkdkdk")
                            .idioma(Idioma.FRANCES)
                            .niveles(nivelService.findById(3))
                            .conversacion(conversacionService.findById(2))
                            .build()
            );
        };

    }
}
