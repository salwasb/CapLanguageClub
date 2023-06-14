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
        List<Asistente> asistentes = new ArrayList<>();

        //Tengo que crear una lista de asistentes en la que voy añadiendo los asistentes por conversación para que
        //no me de error el commandLineRunner e ir añadiendo cada asistente a su correspondiente lista.

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
                                        .asistentes(asistentes)
                                        .numeroAsistentes(4)
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
                                        .numeroAsistentes(4)
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
                                        .numeroAsistentes(3)
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
                                        .numeroAsistentes(2)
                                        .build();
                        Conversacion conversacion6 = Conversacion.builder()
                                        .id(5)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.NATIVO)
                                        .numeroAsistentes(2)
                                        .build();
                        Conversacion conversacion7 = Conversacion.builder()
                                        .id(5)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.NATIVO)
                                        .numeroAsistentes(2)
                                        .build();
                        Conversacion conversacion8 = Conversacion.builder()
                                        .id(5)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.NATIVO)
                                        .numeroAsistentes(2)
                                        .build();

                        conversacionesAsistente1.add(conversacion1);
                        conversacionesAsistente1.add(conversacion2);
                        conversacionesAsistente1.add(conversacion3);
                        conversacionesAsistente1.add(conversacion4);
                        conversacionesAsistente1.add(conversacion5);
                        conversacionesAsistente1.add(conversacion6);
                        conversacionesAsistente1.add(conversacion7);
                        conversacionesAsistente1.add(conversacion8);

                        conversacionService.save(conversacion1);
                        conversacionService.save(conversacion2);
                        conversacionService.save(conversacion3);
                        conversacionService.save(conversacion4);
                        conversacionService.save(conversacion5);
                        conversacionService.save(conversacion6);
                        conversacionService.save(conversacion7);
                        conversacionService.save(conversacion8);

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
