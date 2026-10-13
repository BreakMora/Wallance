package com.wallance.api.service;

import com.wallance.api.models.dto.AuthenticationResponseDTO;
import com.wallance.api.models.dto.LoginRequestDTO;

public interface IAuthenticationService {
    public AuthenticationResponseDTO login(LoginRequestDTO request);
}
