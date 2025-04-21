package com.livestockmanagementapi.model.dto.feedWarehouse;

import java.time.LocalDate;

public class FeedRequest {
    private String feedType;
    private Long quantity;
    private LocalDate date;
    private Long pigPenId;

    public FeedRequest(String feedType, Long quantity, LocalDate date, Long pigPenId) {
        this.feedType = feedType;
        this.quantity = quantity;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }
}

