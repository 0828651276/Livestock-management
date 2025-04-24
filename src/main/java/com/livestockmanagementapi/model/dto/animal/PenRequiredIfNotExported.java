package com.livestockmanagementapi.model.dto.animal;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PenRequiredIfNotExportedValidator.class)
@Documented
public @interface PenRequiredIfNotExported {
    String message() default "Chuồng nuôi phải được chọn cho động vật đang nuôi";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}