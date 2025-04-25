package com.livestockmanagementapi.model.dto.feedWarehouse;

public class FeedInventoryDTO {
    private String feedType;
    private Long remainingQuantity;
    private String note;

    public FeedInventoryDTO(String feedType, Long remainingQuantity, String note) {
        this.feedType = feedType;
        this.remainingQuantity = remainingQuantity;
        this.note = note;
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

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}

