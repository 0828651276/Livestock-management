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

    private LocalDate date;
    private String vaccineType;
    private String note;

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pen;
}

