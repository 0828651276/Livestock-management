package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name= "animal")
public class Animal {//Cá thể (con lợn)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pigId;

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;
    private String name;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private String status;
    private BigDecimal weight;
    private Integer quantity; // Số lượng
}

