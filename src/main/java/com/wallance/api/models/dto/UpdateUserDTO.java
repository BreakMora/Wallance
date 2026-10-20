package com.wallance.api.models.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(
    String username,
    @Email (message = "The email does not have a valid format.")
    String email,
    @Size (min = 4, max = 72)
    String password,
    @Past (message = "The birthday cannot be a future date.")
    LocalDate birthday
) {
    public UpdateUserDTO{
        username = blankToNull(username);
        email = blankToNull(email);
        password = blankToNull(password);
    }

    private static String blankToNull(String value){
        return ( value == null || value.isBlank()) ? null : value;
    }
} 
