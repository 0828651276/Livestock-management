package com.livestockmanagementapi.model.dto.FeedPlan;

public class DailyFeedSummaryDTO {
    private Long feedPlanId;
    private Long penId;
    private String penName;
    private String feedType;
    private Long totalDailyFood;

    public DailyFeedSummaryDTO(Long feedPlanId, Long penId, String penName, String feedType, Long totalDailyFood) {
        this.feedPlanId = feedPlanId;
        this.penId = penId;
        this.penName = penName;
        this.feedType = feedType;
        this.totalDailyFood = totalDailyFood;
    }

    public Long getFeedPlanId() {
        return feedPlanId;
    }

    public void setFeedPlanId(Long feedPlanId) {
        this.feedPlanId = feedPlanId;
    }

    public Long getPenId() {
        return penId;
    }

    public void setPenId(Long pigPenId) {
        this.penId = pigPenId;
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

