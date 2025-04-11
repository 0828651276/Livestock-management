package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name= "export_record")

public class ExportRecord {//Xuất chuồng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pigId;

    private LocalDate exportDate;

    private BigDecimal weight;

    private String status; // Tình trạng xuất: bán, chuyển trại, giết mổ...

    private String note;
}

