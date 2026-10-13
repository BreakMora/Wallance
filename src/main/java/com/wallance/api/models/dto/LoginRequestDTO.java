package com.wallance.api.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record  LoginRequestDTO(
    // String username,
    @NotBlank (message = "The email field is required.")
    @Email (message = "The email does not have a valid format.")
    String email,
    @NotBlank (message = "The password field is required.")
    String password
){

}
