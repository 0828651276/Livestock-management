package com.livestockmanagementapi.model.dto.feedWarehouse;

import java.math.BigDecimal;

public class FeedInventoryDTO {
    private String feedType;
    private String unit;
    private Long remainingQuantity;

    public FeedInventoryDTO(String feedType, String unit, Long remainingQuantity) {
        this.feedType = feedType;
        this.unit = unit;
        this.remainingQuantity = remainingQuantity;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Long remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}

