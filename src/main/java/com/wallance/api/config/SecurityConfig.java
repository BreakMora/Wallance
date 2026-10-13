package com.wallance.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor 
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Protege con ataques que abusan de sesiones coockies.
            .formLogin(AbstractHttpConfigurer::disable)  // Desactiva el formulario login de SPRING.
            .httpBasic(AbstractHttpConfigurer::disable) // Desactiva el Basic Auth (usuario:contraseña en cada petición).
            // STATELESS: Spring no crea sesiones ni recuerda nada entre peticiones. El token se valida desde 0 cada peticion.
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Reglas de autorización: qué rutas son públicas y cuáles exigen autenticación.
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas. /error debe estar abierta para que los errores muestren su body.
                .requestMatchers("/api/v1/auth/login", "/api/v1/error").permitAll()
                .anyRequest().authenticated()  // Cualquier otra ruta exige un usuario autenticado
            )
            // Responde 401 cuando alguien sin autenticar entra a una ruta protegida.
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            // Se ejecuta el filtro del JWT antes de UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Construye la cadena con toda la configuración anterior.
        return http.build();
    }
}
