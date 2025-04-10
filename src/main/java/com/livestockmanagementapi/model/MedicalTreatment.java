package com.livestockmanagementapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class MedicalTreatment {//Khám chữa trị
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private String penCode;      // Mã chuồng

    private String treatmentType; // Loại điều trị (ví dụ: cảm, tiêu chảy...)

    private String veterinarian;  // Bác sĩ/Người chữa trị

    private String medicine;      // Tên thuốc

    private String note;
}

