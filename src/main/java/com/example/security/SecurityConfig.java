package com.example.security;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Bean
    public PasswordEncoder passwordEncoder() {
    
         return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/asistentes/**").permitAll()
            .and().authorizeHttpRequests()
            .requestMatchers("/users/**").permitAll()
            .and().authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/asistentes/**").hasAuthority("ADMIN")
            .and().authorizeHttpRequests()
            .requestMatchers(HttpMethod.PUT, "/asistentes/**").hasAuthority("ADMIN")
            .and().authorizeHttpRequests()
            .requestMatchers(HttpMethod.DELETE, "/asistentes/**").hasAuthority("ADMIN")
           .anyRequest().authenticated().and().httpBasic(withDefaults()).build();
     }
    
}


