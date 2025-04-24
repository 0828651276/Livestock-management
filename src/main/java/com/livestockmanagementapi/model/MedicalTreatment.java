package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name= "medical_treatment")
public class MedicalTreatment {//Khám chữa trị
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pig_id", nullable = false)
    private Animal animal;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pen_id", nullable = false)
    private PigPen pen;

    private String treatmentType; // Loại điều trị (ví dụ: cảm, tiêu chảy...)

    private String veterinarian;  // Bác sĩ/Người chữa trị

    private String medicine;      // Tên thuốc

    private String note;
}
