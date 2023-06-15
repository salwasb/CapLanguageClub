package com.example.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/assistants/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/conversations/**").permitAll();
            auth.requestMatchers("/users/**").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/assistants/**").hasAuthority("ADMIN");
            auth.requestMatchers(HttpMethod.PUT, "/assistants/**").hasAuthority("ADMIN");
            auth.requestMatchers(HttpMethod.DELETE, "/assistants/**").hasAuthority("ADMIN");
            
            auth.requestMatchers(HttpMethod.POST, "/conversations/**").hasAuthority("ADMIN");
            auth.requestMatchers(HttpMethod.PUT, "/conversations/**").hasAuthority("ADMIN");
            auth.requestMatchers(HttpMethod.DELETE, "/conversations/**").hasAuthority("ADMIN");
            auth.anyRequest().authenticated();
            }).httpBasic(Customizer.withDefaults()).build();
     }
    
}
