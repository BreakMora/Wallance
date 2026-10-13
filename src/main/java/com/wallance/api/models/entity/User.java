package com.wallance.api.models.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter // methods
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private LocalDate birthday;

    @Column(nullable=false, name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @Column(nullable=false, name="updated_at")
    private LocalDateTime updatedAt;

    // When the user data is created this save the date
    @PrePersist
    private void onCreated(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // When the user data is updated this save the date
    @PreUpdate
    private void onUpdated(){
        updatedAt = LocalDateTime.now();
    }

}