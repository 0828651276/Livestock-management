package com.livestockmanagementapi.model.dto.feedWarehouse;

public class FeedInventoryDTO {
    private String feedType;
    private Long remainingQuantity;

    public FeedInventoryDTO(String feedType, Long remainingQuantity) {
        this.feedType = feedType;
        this.remainingQuantity = remainingQuantity;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public Long getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Long remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}

