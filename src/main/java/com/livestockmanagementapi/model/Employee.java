package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee { //Nhân viên
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String employeeId; // Now as a business identifier, not the primary key

    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;
    private LocalDate birthDate;
    private String gender;
    private String idCardNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        MANAGER, STAFF
    }
}