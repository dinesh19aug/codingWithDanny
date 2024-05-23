package com.javahabit.springsecurity.entity;

import jakarta.persistence.*;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "Employee", schema = "public")
public class Employee {

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="emailId")
    private String email;

    public Employee() {

    }
}
