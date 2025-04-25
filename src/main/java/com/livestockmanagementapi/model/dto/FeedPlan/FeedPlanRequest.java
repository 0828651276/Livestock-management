package com.livestockmanagementapi.model.dto.FeedPlan;

public class FeedPlanRequest {
    private Long feedPlanId; // optional: nếu null thì là thêm mới, nếu có thì là cập nhật
    private String feedType;
    private Long dailyFood;
    private Long pigPenId;

    public FeedPlanRequest(Long feedPlanId, String feedType, Long dailyFood, Long pigPenId) {
        this.feedPlanId = feedPlanId;
        this.feedType = feedType;
        this.dailyFood = dailyFood;
        this.pigPenId = pigPenId;
    }

    public Long getId() {
        return feedPlanId;
    }

    public void setId(Long id) {
        this.feedPlanId = id;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public Long getDailyFood() {
        return dailyFood;
    }

    public void setDailyFood(Long dailyFood) {
        this.dailyFood = dailyFood;
    }

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }
}

