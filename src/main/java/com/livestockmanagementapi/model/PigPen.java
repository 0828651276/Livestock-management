package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class PigPen {//Chuồng nuôi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee caretaker;

    private LocalDate createdDate;
    private LocalDate closedDate;
    private int quantity;
}

