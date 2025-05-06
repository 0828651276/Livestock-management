package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "feed_plan")
public class FeedPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedType;           // Loại thức ăn
    private Long dailyFood;   // Lượng ăn mỗi ngày

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;

//    @OneToMany(mappedBy = "feedPlan", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FeedHistory> feedHistories = new ArrayList<>();
}

