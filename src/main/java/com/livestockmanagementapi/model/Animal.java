package com.livestockmanagementapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pigId;

    @ManyToOne
    @JoinColumn(name = "pen_id")
    private PigPen pigPen;

    private String name;

    private LocalDate entryDate;

    private LocalDate exitDate;

    private BigDecimal weight;

    private Integer quantity;

    // Trạng thái sức khỏe
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    // Trạng thái nuôi
    @Enumerated(EnumType.STRING)
    private RaisingStatus raisingStatus;

    public enum HealthStatus {
        ACTIVE, SICK
    }

    public enum RaisingStatus {
        RAISING, EXPORTED
    }

    // Tự động đặt các giá trị mặc định khi tạo mới
    @PrePersist
    public void setDefaultValues() {
        if (this.healthStatus == null) {
            this.healthStatus = HealthStatus.ACTIVE;
        }

        if (this.raisingStatus == null) {
            this.raisingStatus = RaisingStatus.RAISING;
        }

        // Khi có ngày xuất chuồng, tự động set trạng thái là EXPORTED
        if (this.exitDate != null) {
            this.raisingStatus = RaisingStatus.EXPORTED;
        }
    }

    // Cập nhật trạng thái khi thay đổi ngày xuất
    @PreUpdate
    public void updateStatus() {
        if (this.exitDate != null) {
            this.raisingStatus = RaisingStatus.EXPORTED;
        }
    }
}