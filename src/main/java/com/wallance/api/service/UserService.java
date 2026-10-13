package com.wallance.api.service;

import org.springframework.stereotype.Service;

import com.wallance.api.mapper.UserMapper;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.repository.IUserRepository;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;


    public UserService(IUserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO findUserById(Integer id) {
        return userRepository.findById(id).map(userMapper::toUserDTO).orElse(null);
    }
    
    @Override
    public UserDTO findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toUserDTO).orElse(null);
    }

    @Override
    public UserDTO getUserData(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserData'");
    }

    @Override
    public UserDTO saveUserData(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUserData'");
    }

    @Override
    public UserDTO modifyUserPassword(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyUserPassword'");
    }

}
