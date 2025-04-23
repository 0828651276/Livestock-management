package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "feed_plan")
public class FeedPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedType;           // Loại thức ăn
    private Long dailyFood;   // Lượng ăn mỗi ngày
    private String unit;              // Đơn vị (kg, g, ...)

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;
}

