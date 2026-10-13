package com.wallance.api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wallance.api.models.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    @Value ("${jwt.secret}")
    private String secretString;
    
    @Value ("${jwt.expiration}")
    private long expirationTime; // 1 dia para expiracion en milisegundos

    private SecretKey secretKey;

    // Convertir el texto en una llave valida para H256
    @PostConstruct
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes());
    }

    public String TokenGenerator(UserDTO userDTO){
        Map<String, Object> hasMap = new HashMap<>();
        hasMap.put("email", userDTO.email());

        return Jwts.builder()
                .subject(userDTO.email())
                .signWith(secretKey)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    // Verifica el token, retorna un objeto Claim para poder obtener datos del token
    private Claims extractClaims(String token){
        return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    // Extrae un subject del token y retorna el valor, retornara null si no esta presente
    public String extractSubject(String token){
        final Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    // Verifica que el token sea valido y devuelve true si completo la verificacion existosa
    public boolean isValidToken(String token, String subject){
        try{
            // retorna true solo si la firma es valida, el token no expiro y el subject coincide
            return subject.equals(extractClaims(token).getSubject());
        } catch(JwtException | IllegalArgumentException e) {
            // retorna false si la verificacion es null o el token es invalido
            return false;
        }
    }
}
