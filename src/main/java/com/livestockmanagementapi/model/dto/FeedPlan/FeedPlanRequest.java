package com.livestockmanagementapi.model.dto.FeedPlan;

public class FeedPlanRequest {
    private Long id; // optional: nếu null thì là thêm mới, nếu có thì là cập nhật
    private String feedType;
    private Long dailyFood;
    private Long pigPenId;

    public FeedPlanRequest(Long id, String feedType, Long dailyFood, Long pigPenId) {
        this.id = id;
        this.feedType = feedType;
        this.dailyFood = dailyFood;
        this.pigPenId = pigPenId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

