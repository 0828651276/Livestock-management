package com.livestockmanagementapi.model.dto.pigpen;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class PigPenDTO {
    private Long penId;
    private String name;
    private LocalDate createdDate;
    private LocalDate closedDate;
    private int quantity;
    private String status;
    private Set<Long> caretakerIds; // Danh sách id nhân viên chăm sóc
}
