package com.livestockmanagementapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity

public class ExportRecord {//Xuất chuồng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pigId;

    private LocalDate exportDate;

    private BigDecimal weight;

    private String status; // Tình trạng xuất: bán, chuyển trại, giết mổ...

    private String note;
}

