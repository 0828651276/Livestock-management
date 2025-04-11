package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name= "vaccination")
public class Vaccination {//Tiêm phòng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String penCode;
    private String vaccineType;
    private String vaccinator;
    private Integer amount;
    private String note;
}

