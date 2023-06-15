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

        private List<Asistente> asistentesConversacion1 = new ArrayList<>();
        private List<Asistente> asistentesConversacion2 = new ArrayList<>();


        @Bean
        public CommandLineRunner sampleData(ConversacionService conversacionService,
                        AsistenteService asistentesService, UserService userService) {

                return args -> {
                        userService.add(User.builder()
                                        .id(1)
                                        .firstName("Yak")
                                        .lastName("Rhom")
                                        .email("yaku@cap.com")
                                        .password("Temp2023$$")
                                        .role(Role.ADMIN)
                                        .build());
                        userService.add(User.builder()
                                        .id(2)
                                        .firstName("Rachida")
                                        .lastName("Ragmi")
                                        .email("rachi@cap.com")
                                        .password("Temp2023$$")
                                        .role(Role.USER)
                                        .build());

                        Conversacion conversacion1 = Conversacion.builder()
                                        .id(1)
                                        .titulo("Anglais ")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 11))
                                        .hora(LocalTime.of(20, 30, 20))
                                        .modo(Modo.ONLINE)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.A)
                                        .asistentes(asistentesConversacion1)
                                        .build();

                        Conversacion conversacion2 = Conversacion.builder()
                                        .id(2)
                                        .titulo("Anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 10))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.SINNIVEL)
                                        .asistentes(asistentesConversacion1)
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
                                        .asistentes(asistentesConversacion2)
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
                                        .asistentes(asistentesConversacion1)
                                        .build();

                        Conversacion conversacion5 = Conversacion.builder()
                                        .id(5)
                                        .titulo("anglais")
                                        .fecha(LocalDate.of(2023, Month.DECEMBER, 6))
                                        .hora(LocalTime.of(20, 15, 00))
                                        .modo(Modo.PRESENCIAL)
                                        .lugar("Planta 2")
                                        .idioma(Idioma.INGLES)
                                        .nivel(Nivel.NATIVO)
                                        .asistentes(asistentesConversacion1)
                                        .build();

                        conversacionesAsistente1.add(conversacion1);
                        conversacionesAsistente1.add(conversacion2);
                        conversacionesAsistente1.add(conversacion3);
                        conversacionesAsistente2.add(conversacion5);
                        conversacionesAsistente3.add(conversacion4);

                        conversacionService.save(conversacion1);
                        conversacionService.save(conversacion2);
                        conversacionService.save(conversacion3);
                        conversacionService.save(conversacion4);
                        conversacionService.save(conversacion5);

                       
                         Asistente asistente1 = Asistente.builder()
                                                        .id(1)
                                                        .nombre("Nawal")
                                                        .apellidos("AAA")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("jgjfj@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.A)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build();
                        
                         Asistente asistente2 =  Asistente.builder()
                                                        .id(2)
                                                        .nombre("Mimi")
                                                        .apellidos("BBB")
                                                        .correo("kffkk@cap.com")
                                                        .imagenAsistente("test.jpeg")
                                                        .idioma(Idioma.INGLES)
                                                        .nivel(Nivel.NATIVO)
                                                        .conversacion(conversacionesAsistente2)
                                                        .build();

                         Asistente asistente3 = Asistente.builder()
                                                        .id(3)
                                                        .nombre("Richi")
                                                        .apellidos("CCC")
                                                        .correo("kdkdkdk@cap.com")
                                                        .imagenAsistente("test.jpeg")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.B)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build();

                        Asistente asistente4 =  Asistente.builder()
                                                        .id(4)
                                                        .nombre("Angel")
                                                        .apellidos("FGF")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("ihgf@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.NATIVO)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build();

                       
                        Asistente asistente5 =  Asistente.builder()
                                                        .id(5)
                                                        .nombre("Victor")
                                                        .apellidos("MMM")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("ods@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.A)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build();

                       
                        Asistente asistente6 =  Asistente.builder()
                                                        .id(6)
                                                        .nombre("Nina")
                                                        .apellidos("VVV")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("fdfd@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.B)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build();

                        
                        Asistente asistente7 =  Asistente.builder()
                                                        .id(7)
                                                        .nombre("Alex")
                                                        .apellidos("PPP")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("sewe@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.C)
                                                        .conversacion(conversacionesAsistente2)
                                                        .build();

                        
                        Asistente asistente8 =  Asistente.builder()
                                                        .id(8)
                                                        .nombre("Lili")
                                                        .apellidos("FAS")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("sew@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.SINNIVEL)
                                                        .conversacion(conversacionesAsistente1)
                                                        .build();

                        
                        Asistente asistente9 =  Asistente.builder()
                                                        .id(9)
                                                        .nombre("Paula")
                                                        .apellidos("TTT")
                                                        .imagenAsistente("test.jpeg")
                                                        .correo("erer@cap.com")
                                                        .idioma(Idioma.FRANCES)
                                                        .nivel(Nivel.SINNIVEL)
                                                        .conversacion(conversacionesAsistente3)
                                                        .build();

                        asistentesService.saveAsistente(asistente1);
                        asistentesService.saveAsistente(asistente2);
                        asistentesService.saveAsistente(asistente3);
                        asistentesService.saveAsistente(asistente4); 
                        asistentesService.saveAsistente(asistente5);
                        asistentesService.saveAsistente(asistente6);
                        asistentesService.saveAsistente(asistente7);
                        asistentesService.saveAsistente(asistente8);
                        asistentesService.saveAsistente(asistente9);
                        
                        asistentesConversacion1.add(asistente1);
                        asistentesConversacion1.add(asistente2);
                        asistentesConversacion1.add(asistente3);
                        asistentesConversacion1.add(asistente4);
                        asistentesConversacion1.add(asistente5);
                        asistentesConversacion1.add(asistente6);
                        asistentesConversacion1.add(asistente7);
                        asistentesConversacion1.add(asistente8);

                        asistentesConversacion2.add(asistente9);
                        asistentesConversacion2.add(asistente3);
                        asistentesConversacion2.add(asistente5);
                        asistentesConversacion2.add(asistente7);

                };
               
        }
}
