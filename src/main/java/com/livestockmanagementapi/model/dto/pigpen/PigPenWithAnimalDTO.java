package com.livestockmanagementapi.model.dto.pigpen;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PigPenWithAnimalDTO {
    private Long penId;
    private String penName;
    private String animalNames; // Có thể là chuỗi ghép các tên động vật
} 