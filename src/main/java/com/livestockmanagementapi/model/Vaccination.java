package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name= "vaccination")
public class Vaccination {//Tiêm phòng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pig_id", nullable = false)
    private Animal animal;

    private LocalDate date;
    private String vaccine;
    private String note;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Thêm enum Status
    public enum Status {
        SCHEDULED,
        COMPLETED
    }
}

