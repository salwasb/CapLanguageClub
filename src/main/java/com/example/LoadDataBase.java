package com.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserService;

@Configuration

public class LoadDataBase {

        private List<Conversacion> conversacionesAsistente1 = new ArrayList<>();
        private List<Conversacion> conversacionesAsistente2 = new ArrayList<>();
        private List<Conversacion> conversacionesAsistente3 = new ArrayList<>();

        @Bean
        public CommandLineRunner sampleData(ConversacionService conversacionService,
                        AsistenteService asistentesService, UserService userService) {

                return args -> {
                        userService.add(User.builder()
                                        .id(1)
                                        .firstName("Yak")
                                        .lastName("Rhom")
                                        .email("y@cap.com")
                                        .password("Temp2023$$")
                                        .role(Role.ADMIN)
                                        .build());

                        Conversacion conversacion1 = Conversacion.builder()
                                        .id(1)
                                        .titulo("francais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                                        .hora(LocalTime.of(20, 30, 20))
                                        .modo(Modo.ONLINE)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.FRANCES)
                                        .nivel(Nivel.A)
                                        .numeroAsistentes(5)
                                        .build();

                        Conversacion conversacion2 = Conversacion.builder()
                                        .id(2)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.SINNIVEL)
                                        .numeroAsistentes(5)
                                        .build();

                        Conversacion conversacion3 = Conversacion.builder()
                                        .id(3)
                                        .titulo("francais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 05))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.SINNIVEL)
                                        .numeroAsistentes(4)
                                        .build();

                         Conversacion conversacion4 = Conversacion.builder()
                                        .id(4)
                                        .titulo("francais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 20))
                                        .hora(LocalTime.of(20, 30, 20))
                                        .modo(Modo.ONLINE)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.FRANCES)
                                        .nivel(Nivel.C)
                                        .numeroAsistentes(5)
                                        .build();

                        Conversacion conversacion5 = Conversacion.builder()
                                        .id(5)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.NATIVO)
                                        .numeroAsistentes(5)
                                        .build();

                        conversacionesAsistente1.add(conversacion1);
                        conversacionesAsistente1.add(conversacion2);
                        conversacionesAsistente1.add(conversacion3);
                        conversacionesAsistente2.add(conversacion5);
                        conversacionesAsistente3.add(conversacion5);
                        conversacionesAsistente3.add(conversacion4);

                        conversacionService.save(conversacion1);
                        conversacionService.save(conversacion2);
                        conversacionService.save(conversacion3);
                        conversacionService.save(conversacion4);
                        conversacionService.save(conversacion5);

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(1)
                                                        .nombre("Nawal")
                                                        .apellidos("AAA")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("jgjfj@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(2)
                                                        .nombre("Mimi")
                                                        .apellidos("BBB")
                                                        .correo("kffkk@cap.com")
                                                        .idioma(Idioma.INGLES)
                                                        .conversacion(conversacionesAsistente2)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(3)
                                                        .nombre("Richi")
                                                        .apellidos("CCC")
                                                        .correo("kdkdkdk@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(4)
                                                        .nombre("Angel")
                                                        .apellidos("FGF")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("ihgf@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(5)
                                                        .nombre("Victor")
                                                        .apellidos("MMM")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("ods@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(6)
                                                        .nombre("Nina")
                                                        .apellidos("VVV")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("fdfd@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(7)
                                                        .nombre("Alex")
                                                        .apellidos("PPP")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("sewe@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente2)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(8)
                                                        .nombre("Lili")
                                                        .apellidos("FAS")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("sew@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build());

                        asistentesService.saveAsistente(
                                        Asistente.builder()
                                                        .id(9)
                                                        .nombre("Paula")
                                                        .apellidos("TTT")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("erer@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build());
                };
        }
}
