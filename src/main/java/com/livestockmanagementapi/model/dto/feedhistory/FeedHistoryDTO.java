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
    private String pigPenName;    // Chỉ lấy tên chuồng
    private String animalName;    // Chỉ lấy tên động vật nếu có
    private String feedType;      // Lấy loại thức ăn từ FeedPlan
    private LocalDateTime feedingTime;
    private Long dailyFood;
    private String createdByName; // Tên người tạo
}
