package com.livestockmanagementapi.model.dto.medical;

import com.livestockmanagementapi.model.MedicalTreatment;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalTreatmentDTO {
    private Long id;
    private Long pigId;
    private Long penId;
    private LocalDate date;
    private String treatmentType;
    private String veterinarian;
    private String medicine;
    private String note;

    public MedicalTreatmentDTO(MedicalTreatment mt) {
        this.id = mt.getId();
        this.pigId = mt.getAnimal() != null ? mt.getAnimal().getPigId() : null;
        this.penId = mt.getPen() != null ? mt.getPen().getPenId() : null;
        this.date = mt.getDate();
        this.treatmentType = mt.getTreatmentType();
        this.veterinarian = mt.getVeterinarian();
        this.medicine = mt.getMedicine();
        this.note = mt.getNote();
    }
}
