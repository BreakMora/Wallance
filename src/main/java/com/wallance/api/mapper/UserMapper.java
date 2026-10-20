package com.wallance.api.mapper;

import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UpdateUserDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.models.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDTO toUserDTO(User user);

    // User toEntity(UserDTO userDTO);

    // Con esta etiqueta ignoramos el id
    @Mapping (target = "id", ignore = true)
    User toEntity(CreateUserDTO newUserDTO);

    // Mappeo de valores no nulos en el dto
    @Mapping (target = "password", ignore = true)
    void updateUserFromDTO(UpdateUserDTO updateUserDTO, @MappingTarget User user);
}
