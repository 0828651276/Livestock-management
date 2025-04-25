package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notification_employee")
public class NotificationEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;
}
