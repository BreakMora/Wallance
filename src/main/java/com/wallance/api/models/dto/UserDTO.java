package com.wallance.api.models.dto;

import java.time.LocalDate;

public record UserDTO(
    Integer id,
    String username,
    String email,
    LocalDate birthday
) {

}