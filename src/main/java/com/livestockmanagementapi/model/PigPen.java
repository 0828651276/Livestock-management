package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name= "pig_pen")
public class PigPen {//Chuồng nuôi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee caretaker;
    private String name;
    private LocalDate createdDate;
    private LocalDate closedDate;
    private int quantity;
}

