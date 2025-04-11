package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PigPen {//Chuồng nuôi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String penId; // Now as a business identifier, not the primary key

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee caretaker;

    private LocalDate createdDate;
    private LocalDate closedDate;
    private int quantity;
}