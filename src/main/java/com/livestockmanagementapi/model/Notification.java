package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
        name = "notification_pigpen",
        joinColumns = @JoinColumn(name = "notification_id"),
        inverseJoinColumns = @JoinColumn(name = "pen_id")
    )
    private Set<PigPen> pigPens = new HashSet<>(); // Danh sách chuồng liên quan
}
