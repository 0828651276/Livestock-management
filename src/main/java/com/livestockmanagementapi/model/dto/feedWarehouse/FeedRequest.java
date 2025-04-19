package com.livestockmanagementapi.model.dto.feedWarehouse;

import java.time.LocalDate;

public class FeedRequest {
    private String feedType;
    private Long quantity;
    private String unit;
    private LocalDate date;
    private Long feedBatchId;
    private Long pigPenId;

    public FeedRequest(String feedType, Long quantity, String unit, LocalDate date, Long feedBatchId, Long pigPenId) {
        this.feedType = feedType;
        this.quantity = quantity;
        this.unit = unit;
        this.date = date;
        this.feedBatchId = feedBatchId;
        this.pigPenId = pigPenId;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getFeedBatchId() {
        return feedBatchId;
    }

    public void setFeedBatchId(Long feedBatchId) {
        this.feedBatchId = feedBatchId;
    }

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }
}

