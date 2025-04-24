package com.livestockmanagementapi.model.dto.animal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PenRequiredIfNotExportedValidator implements ConstraintValidator<PenRequiredIfNotExported, AnimalRequest> {

    @Override
    public void initialize(PenRequiredIfNotExported constraintAnnotation) {
        // Không cần khởi tạo gì thêm
    }

    @Override
    public boolean isValid(AnimalRequest animal, ConstraintValidatorContext context) {
        // Nếu đang ở trạng thái RAISING thì penId là bắt buộc
        if ("RAISING".equals(animal.getRaisingStatus()) && animal.getPenId() == null) {
            return false;
        }

        // Kiểm tra logic bổ sung: nếu có exitDate thì raisingStatus phải là EXPORTED
        if (animal.getExitDate() != null && !"EXPORTED".equals(animal.getRaisingStatus())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Động vật có ngày xuất chuồng phải có trạng thái EXPORTED")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}