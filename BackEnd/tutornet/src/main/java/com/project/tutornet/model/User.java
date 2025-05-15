package com.project.tutornet.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

 
    private String email;


    private String password;


    private String firstName;


    private String lastName;


    private String userRole;

    
    @OneToMany(mappedBy = "user")
    private List<Student> students;

    @OneToMany(mappedBy = "user")
    private List<Tutor> tutors;


   
}
