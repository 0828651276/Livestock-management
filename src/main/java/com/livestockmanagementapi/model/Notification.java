package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name= "notification")

public class Notification { //Thông báo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDate postedDate = LocalDate.now();
}

