package com.wallance.api.mapper;

import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
import org.mapstruct.Mapping;

import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.models.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    // User toEntity(UserDTO userDTO);

    // Con esta etiqueta ignoramos el id
    @Mapping (target = "id", ignore = true)
    User toEntity(CreateUserDTO newUserDTO);
}
