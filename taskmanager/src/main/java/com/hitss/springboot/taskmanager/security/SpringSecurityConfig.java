package com.hitss.springboot.taskmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hitss.springboot.taskmanager.security.filter.JwtAuthenticationFilter;
import com.hitss.springboot.taskmanager.security.filter.JwtValidationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/login", "/users/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/users", "/users/register").permitAll()
                
                // Endpoints de tareas con control de roles
                .requestMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/tasks/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/tasks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")
                
                // Cualquier otro endpoint requiere autenticación
                .anyRequest().authenticated()
            )
            // Agregamos filtros JWT
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .build();
    }

}
