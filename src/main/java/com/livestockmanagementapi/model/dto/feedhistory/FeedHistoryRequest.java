package com.livestockmanagementapi.model.dto.feedhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedHistoryRequest {
    private Long pigPenId;
    private Long animalId;
    private Long feedPlanId;
    private LocalDateTime feedingTime;
    private Long dailyFood;
    private Long createdById;
}
