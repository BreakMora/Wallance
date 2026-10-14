package com.wallance.api.models.dto;

import java.time.LocalDate;
import java.util.Locale;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
    @NotBlank (message = "The username field is required.")
    String username,
    @NotBlank (message = "The email field is required.")
    @Email (message = "The email does not have a valid format.")
    String email,
    @NotBlank (message = "The password field is required.")
    @Size (min = 4, max = 72)
    String password,
    @NotNull (message = "The birthday field is required.")
    @Past (message = "The birthday cannot be a future date.")
    LocalDate birthday
){

    public CreateUserDTO {
        if (email != null)
            email = email.trim().toLowerCase(Locale.ROOT);
    }
}
