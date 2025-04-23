package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name= "notification")
public class Notification { //Thông báo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    @Column(name = "posted_at")
    private LocalDateTime postedAt = LocalDateTime.now();

    @Column(name = "is_read", nullable = false)
    private boolean read = false;
}
