package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee { //Nhân viên
    @Id
    private String employeeId;

    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;
    private LocalDate birthDate;
    private String gender;
    private String idCardNumber;

    private String imagePath = "";

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        MANAGER, STAFF
    }

    @PrePersist
    public void generateId() {
        if (this.employeeId == null || this.employeeId.isEmpty()) {
            this.employeeId = "NV" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        }

        if (this.role == null) {
            this.role = Role.STAFF;
        }
    }
}

