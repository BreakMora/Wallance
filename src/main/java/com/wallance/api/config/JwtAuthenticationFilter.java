package com.wallance.api.config;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wallance.api.security.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
// Heredade de la OncePerRequestFilter la posibilidad de que el filtro unicamente corra una sola vez por peticion
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    // Eliminar el prefijo de "Bearer " de la consulta para obtener el token
    private static final String BEARE_STRING = "Bearer ";

    // Lectura del token
    private final JwtService jwtService;
    
    // Metodo que ejecuta SPRING en cada peticion HTTP al entrar a la API
    @Override
    protected void doFilterInternal(HttpServletRequest request, // Peticion entrante (headers, body, ruta)
                                    HttpServletResponse response, // Respuesta de la api al usuario
                                    FilterChain filterChain) // Cadena de filtros
            throws ServletException, IOException {
        
        // 1. Leer el header en la peticion
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 2. Si el header existe y tiene formato de Bearer
        if (header != null && header.startsWith(BEARE_STRING)){
            // 3. Quitar el "Bearer " del token
            String token = header.substring(BEARE_STRING.length());
            try {
                // 4. Verirficar firma y expiracion
                String subjeString = jwtService.extractSubject(token);
                // 5. Crea el objeto que representa "Usuario autenticado"
                var authenticated = new UsernamePasswordAuthenticationToken(subjeString, null, List.of());
                // 6. Guardar en el SecurityContext, la "memoria" de Spring Security para esta peticion
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            } catch (JwtException | IllegalArgumentException e) {
                // Token mal formado, firma invalida o vencido (JwtException)
                // Token null (IllegalArgumentException)
                SecurityContextHolder.clearContext(); // Se limpia el contexto para que la peticion quede sin autenticar
            }
        }

        // 7. Se continua con el siguiente filtro.
        filterChain.doFilter(request, response);
    }

}
