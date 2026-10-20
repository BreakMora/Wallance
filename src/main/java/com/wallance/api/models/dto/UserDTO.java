package com.wallance.api.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDTO(
    Integer id,
    String username,
    String email,
    LocalDate birthday,
    LocalDateTime updatedAt
) {

}