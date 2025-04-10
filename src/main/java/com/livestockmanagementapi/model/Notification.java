package com.livestockmanagementapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Notification { //Thông báo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String content;

    private LocalDate postedDate = LocalDate.now();
}

