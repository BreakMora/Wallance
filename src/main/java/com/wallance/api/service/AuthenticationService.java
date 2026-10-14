package com.wallance.api.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallance.api.exception.BusinessException;
import com.wallance.api.exception.ResourceNotFoundException;
import com.wallance.api.mapper.UserMapper;
import com.wallance.api.models.dto.AuthenticationResponseDTO;
import com.wallance.api.models.dto.LoginRequestDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.models.entity.User;
import com.wallance.api.repository.IUserRepository;
import com.wallance.api.security.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
class AuthenticationService implements IAuthenticationService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        // Busqueda del usuario mediante el email del usuario
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new BusinessException(HttpStatus.UNAUTHORIZED, "Invalid Credentials"));

        // validar contraseña con el hash alojado en la base de datos
        validatePassword(request.password(), user.getPassword());

        // retorna el JWT en formato cadena al frontend
        return new AuthenticationResponseDTO(jwtService.TokenGenerator(userMapper.toUserDTO(user)));
    }

    private UserDTO findUserByEmail(String email){
        return this.userRepository.findByEmail(email).map(userMapper::toUserDTO).orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    private void validatePassword(String passwordRaw, String passwordHash){
        if(!passwordEncoder.matches(passwordRaw, passwordHash))
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
    }

}
