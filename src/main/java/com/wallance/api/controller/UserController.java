package com.wallance.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallance.api.models.dto.CreateUserDTO;
import com.wallance.api.models.dto.UserDTO;
import com.wallance.api.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/users")
public class UserController {

    // private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping ("/{id}")
    public ResponseEntity<UserDTO> findUserByID(
        @PathVariable Integer id //Recibe el id del usuario
    ){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping ("")
    public ResponseEntity<List<UserDTO>> listUsers(){
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping ("")
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody CreateUserDTO newUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUserData(newUserDTO));
    }
}
