package com.wallance.api.service;

import java.util.List;

import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UpdateUserDTO;
import com.wallance.api.models.dto.UserDTO;

public interface IUserService {
    public UserDTO findUserById(Integer id);
    public UserDTO findUserByEmail(String email);
    public List<UserDTO> listUsers();
    public UserDTO getUserData(UserDTO user);
    public UserDTO saveUserData(CreateUserDTO newUserDTO);
    public UserDTO modifyUser(Integer id, UpdateUserDTO updateUserDTO);
    public void deleteUser(Integer id);
}
