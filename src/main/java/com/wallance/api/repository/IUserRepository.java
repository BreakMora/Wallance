package com.wallance.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallance.api.models.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email); // Spring Data JPA lee "findBy" + "Email" y sabe que debe buscar por la propiedad 'email'
    Boolean existsByEmail(String email); // Spring Data JPA lee "exist" + "Email" y sabe que debe verificar por la propiedad 'email'
}
