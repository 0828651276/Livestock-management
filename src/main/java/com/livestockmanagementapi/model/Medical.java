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
@Table(name = "medical")
public class Medical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pig_id", nullable = false)
    private Animal animal;

    private LocalDate treatmentDate;

    @Enumerated(EnumType.STRING)
    private TreatmentMethod treatmentMethod;

    private String veterinarian;

    private String notes;

    public enum TreatmentMethod {
        INJECTION,
        ORAL
    }
} 