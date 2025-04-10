package com.livestockmanagementapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

import java.math.BigDecimal;
@Data


import java.math.BigDecimal;


@Entity
public class FeedBatch {//Quản lý thức ăn (đàn)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String herdCode;
    private String feedType;
    private BigDecimal quantity;
    private String unit;
}

