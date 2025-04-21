package com.livestockmanagementapi.model.dto.FeedPlan;

public class DailyFeedSummaryDTO {
    private Long pigPenId;
    private String penName;
    private String feedType;
    private Long totalDailyFood;

    public DailyFeedSummaryDTO(Long pigPenId, String penName, String feedType, Long totalDailyFood) {
        this.pigPenId = pigPenId;
        this.penName = penName;
        this.feedType = feedType;
        this.totalDailyFood = totalDailyFood;
    }

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public Long getTotalDailyFood() {
        return totalDailyFood;
    }

    public void setTotalDailyFood(Long totalDailyFood) {
        this.totalDailyFood = totalDailyFood;
    }

}

