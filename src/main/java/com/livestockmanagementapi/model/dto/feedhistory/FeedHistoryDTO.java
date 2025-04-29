package com.livestockmanagementapi.model.dto.feedhistory;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.dto.pigpen.PigPenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedHistoryDTO {
    private Long id;
    private PigPenDTO pigPen;
    private Animal animal;
    private FeedPlan feedPlan;
    private LocalDateTime feedingTime;
    private Long feedAmount;
    private String notes;
    private Employee createdBy;
}
