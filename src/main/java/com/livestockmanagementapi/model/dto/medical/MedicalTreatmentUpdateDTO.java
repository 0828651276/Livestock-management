package com.livestockmanagementapi.model.dto.medical;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalTreatmentUpdateDTO {
    private Long pigId;      // ID của Animal (pigId)
    private Long penId;
    private LocalDate date;
    private String treatmentType;
    private String veterinarian;
    private String medicine;
    private String note;
}
