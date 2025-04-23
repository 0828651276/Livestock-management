package com.livestockmanagementapi.model.dto.animal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PenRequiredIfNotExportedValidator implements ConstraintValidator<PenRequiredIfNotExported, AnimalRequest> {
    @Override
    public boolean isValid(AnimalRequest animalRequest, ConstraintValidatorContext context) {
        if (animalRequest == null) return true;
        String status = animalRequest.getStatus();
        // Nếu status là exported thì không cần validate penId
        if (status != null && status.equalsIgnoreCase("exported")) {
            return true;
        }
        // Nếu status khác exported thì penId phải khác null
        return animalRequest.getPenId() != null;
    }
}
