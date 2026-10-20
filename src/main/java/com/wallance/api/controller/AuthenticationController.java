package com.wallance.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallance.api.models.dto.AuthenticationResponseDTO;
import com.wallance.api.models.dto.LoginRequestDTO;
import com.wallance.api.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/auth")
public class AuthenticationController {
    
    private final IAuthenticationService authenticationService;
    
    public AuthenticationController(IAuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(loginRequest));
    }

}
