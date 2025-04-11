package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Entity
@Table(name= "feed_batch")

public class FeedBatch {//Quản lý thức ăn (đàn)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String herdCode;
    private String feedType;
    private BigDecimal quantity;
    private String unit;
}

