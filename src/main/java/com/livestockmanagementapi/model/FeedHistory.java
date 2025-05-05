package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feed_history")
public class FeedHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;

    @ManyToOne
    @JoinColumn(name = "pig_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "feed_plan_id")
    private FeedPlan feedPlan;

    private Long dailyFood; // Lượng thức ăn từ FeedPlan

    private LocalDateTime feedingTime;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Employee createdBy;
}
