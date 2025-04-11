package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {//Cá thể (con lợn)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pigId; // Now as a business identifier, not the primary key

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;

    private LocalDate entryDate;
    private LocalDate exitDate;
    private String status;
    private BigDecimal weight;
}