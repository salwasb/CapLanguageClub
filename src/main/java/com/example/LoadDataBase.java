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

@Configuration
public class LoadDataBase {

    // public static void increment(){
    //     int count=0;
    //     count++;
    // }
    // public static void decrement(){
    //     int count=0;
    //     count--;
    // }
    @Bean
    public CommandLineRunner sampleData(ConversacionService conversacionService,
            AsistenteService asistentesService) {

        return args -> {
                conversacionService.save(
                    Conversacion.builder()
                            .id(1)
                            .titulo("francais")
                            .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                            .hora(LocalTime.of(22, 30, 20))
                            .modo(Modo.ONLINE)
                            .lugar("Planta 2")
                            .idioma(Idioma.FRANCES)
                            .nivel(Nivel.A)
                            .numeroAsistentes(5)
                            .build());
                conversacionService.save(
                    Conversacion.builder()
                            .id(2)
                            .titulo("anglais")
                            .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                            .hora(LocalTime.of(23, 15, 00))
                            .modo(Modo.PRESENCIAL)
                            .lugar("Planta 2")
                            .idioma(Idioma.INGLES)
                            .nivel(Nivel.SINNIVEL)
                            .numeroAsistentes(5)
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

                asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(2)
                            .nombre("Mimi")
                            .apellidos("BBB")
                            .correo("kffkk@cap.com")
                            .idioma(Idioma.INGLES)
                            .conversacion(conversacionService.findById(2))
                            .build());
                asistentesService.saveAsistente(
                    Asistente.builder()
                            .id(3)
                            .nombre("Richi")
                            .apellidos("CCC")
                            .correo("kdkdkdk@cap.com")
                            .idioma(Idioma.FRANCES)
                            .conversacion(conversacionService.findById(2))
                            .build());
        };

    }
}
