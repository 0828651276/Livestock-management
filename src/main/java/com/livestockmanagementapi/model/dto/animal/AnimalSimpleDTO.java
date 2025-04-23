package com.livestockmanagementapi.model.dto.animal;

import com.livestockmanagementapi.model.Animal;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnimalSimpleDTO {
    private Long pigId;
    private String name;
    private String status;
    private BigDecimal weight;
    private Integer quantity;

    public AnimalSimpleDTO(Animal animal) {
        this.pigId = animal.getPigId();
        this.name = animal.getName();
        this.status = animal.getStatus();
        this.weight = animal.getWeight();
        this.quantity = animal.getQuantity();
    }
}
