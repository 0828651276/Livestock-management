package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Animal { // Cá thể

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pigId; // đổi từ String sang Long để tự động tăng

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;

    private String name;

    private LocalDate entryDate;
    private LocalDate exitDate;
    private String status;
    private BigDecimal weight;
}
