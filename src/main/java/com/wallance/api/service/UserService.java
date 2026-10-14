package com.wallance.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallance.api.exception.BusinessException;
import com.wallance.api.mapper.UserMapper;
import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.models.entity.User;
import com.wallance.api.repository.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findUserById(Integer id) {
        return userRepository.findById(id).map(userMapper::toUserDTO).orElse(null);
    }
    
    @Override
    public UserDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toUserDTO).orElse(null);
    }

    @Override
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
        
        assertEmailAvailable(newUserDTO.email());

        User newUser = userMapper.toEntity(newUserDTO);

        newUser.setPassword(passwordEncoder.encode(newUserDTO.password()));
        
        newUser = userRepository.save(newUser);
        
        return userMapper.toUserDTO(newUser);
    }

    @Override
    public UserDTO modifyUserPassword(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyUserPassword'");
    }

    private void assertEmailAvailable(String email){
        if(userRepository.existsByEmail(email))
            throw new BusinessException(HttpStatus.CONFLICT, "The email is already in use.");
    }

}
