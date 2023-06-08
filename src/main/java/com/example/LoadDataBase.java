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
import com.example.entities.Nivel;
import com.example.service.AsistenteService;
import com.example.service.ConversacionService;
import com.example.service.NivelService;

@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(ConversacionService conversacionService, 
                                            AsistenteService asistentesService,
                                                       NivelService nivelService) {

        return args -> {
            nivelService.save(Nivel.builder()
                    .id(1)
                    .nombre("Nivel A")
                    .build());
            conversacionService.save(
                    Conversacion.builder()
                            .id(1)
                            .titulo("francais")
                            .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                            .hora(LocalTime.of(10,15,00))
                            .modo(Modo.ONLINE)
                            .lugar("Planta 2")
                            .idioma(Idioma.FRANCES)
                            .numeroAsistentes(5)
                            // .nivel(nivelService.findById(1))
                            .build());

            asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(1)
                            .nombre("Nawal")
                            .apellidos("AAA")
                            .correo("jgjfj@cap.com")
                            .idioma(Idioma.FRANCES)
                            .conversacion(conversacionService.findById(1))
                            .build());

            // asistentesService.saveAsistente(
            // Asistente.builder()
            // .id(2)
            // .nombre("Mimi")
            // .apellidos("BBB")
            // .correo("kffkkf")
            // .idioma(Idioma.INGLES)
            // .niveles(nivelService.findById(2))
            // .conversacion(conversacionService.findById(2))
            // .build()
            // );
            // asistentesService.saveAsistente(
            // Asistente.builder()
            // .id(3)
            // .nombre("Richi")
            // .apellidos("CCC")
            // .correo("kdkdkdk")
            // .idioma(Idioma.FRANCES)
            // .niveles(nivelService.findById(3))
            // .conversacion(conversacionService.findById(2))
            // .build()
            // );
        };

    }
}
