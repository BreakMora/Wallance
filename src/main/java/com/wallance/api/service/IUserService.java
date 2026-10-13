package com.wallance.api.service;

import com.wallance.api.models.dto.UserDTO;

public interface IUserService {
    public UserDTO findUserById(Integer id);
    public UserDTO findUserByEmail(String email);
    public UserDTO getUserData(UserDTO user);
    public UserDTO saveUserData(UserDTO user);
    public UserDTO modifyUserPassword(UserDTO user);
}
