package com.project.tutornet.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Student extends User {


private int age;
private String classLevel;

    @OneToMany(mappedBy = "student")
    private List<Booking> bookings;

}