package com.project.tutornet.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance (strategy=InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID userId;

 
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userRole;


   
}
