package com.wallance.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallance.api.exception.BusinessException;
import com.wallance.api.exception.ResourceNotFoundException;
import com.wallance.api.mapper.UserMapper;
import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UpdateUserDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.models.entity.User;
import com.wallance.api.repository.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional (readOnly = true)
    public UserDTO findUserById(Integer id) {
        return userRepository.findById(id).map(userMapper::toUserDTO).orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }
    
    @Override
    @Transactional (readOnly = true)
    public UserDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toUserDTO).orElseThrow(() -> new ResourceNotFoundException("Usuario", email));
    }

    @Override
    @Transactional (readOnly = true)
    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDTO).toList();
    }

    @Override
    public UserDTO getUserData(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserData'");
    }

    @Override
    public UserDTO saveUserData(CreateUserDTO newUserDTO) {
        // verificar que el email no haya sido actualizado
        assertEmailAvailable(newUserDTO.email());
        // cambiar el DTO a una entidad usable
        User newUser = userMapper.toEntity(newUserDTO);
        // hashear la contraseña
        newUser.setPassword(passwordEncoder.encode(newUserDTO.password()));
        // guardar usuario
        newUser = userRepository.save(newUser);
        // retornar el DTO a la respuestas
        return userMapper.toUserDTO(newUser);
    }

    @Override
    public UserDTO modifyUser(Integer id, UpdateUserDTO updateUserDTO) {
        // busqueda de usuario por el id
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        // se asigna los datos del DTO a la entidad
        userMapper.updateUserFromDTO(updateUserDTO, user);
        // verificar que la contraseña y asignarla
        if (updateUserDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.password()));
        }
        // se guarda el usuario
        userRepository.save(user);
        // retorna el DTO a la respuesta
        return userMapper.toUserDTO(user);
    }

    private void assertEmailAvailable(String email){
        if(userRepository.existsByEmail(email))
            throw new BusinessException(HttpStatus.CONFLICT, "The email is already in use.");
    }

}
