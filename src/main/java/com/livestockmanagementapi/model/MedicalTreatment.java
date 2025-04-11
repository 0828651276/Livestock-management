package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name= "medical_treatment")
public class MedicalTreatment {//Khám chữa trị
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String penCode;      // Mã chuồng

    private String treatmentType; // Loại điều trị (ví dụ: cảm, tiêu chảy...)

    private String veterinarian;  // Bác sĩ/Người chữa trị

    private String medicine;      // Tên thuốc

    private String note;
}

