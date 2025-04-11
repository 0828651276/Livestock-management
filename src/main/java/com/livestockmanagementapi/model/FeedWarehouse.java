package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name= "feed_warehouse")
public class FeedWarehouse {//Kho thức ăn
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedType;
    private BigDecimal quantity;
    private String unit;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public enum TransactionType {
        IMPORT, EXPORT
    }
}

