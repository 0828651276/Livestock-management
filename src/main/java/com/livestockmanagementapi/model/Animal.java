package com.livestockmanagementapi.model;


import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
public class Animal { // Cá thể

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pigId; // đổi từ String sang Long để tự động tăng

@Entity
public class Animal {//Cá thể (con lợn)
    @Id
    private String pigId;


    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;


    private String name;


    private LocalDate entryDate;
    private LocalDate exitDate;
    private String status;
    private BigDecimal weight;
}

