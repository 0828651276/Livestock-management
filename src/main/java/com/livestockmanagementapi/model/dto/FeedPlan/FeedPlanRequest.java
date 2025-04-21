package com.livestockmanagementapi.model.dto.FeedPlan;

import java.time.LocalDate;

public class FeedPlanRequest {
    private Long id; // optional: nếu null thì là thêm mới, nếu có thì là cập nhật
    private String feedType;
    private Long dailyFood;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long pigPenId;

    public FeedPlanRequest(Long id, String feedType, Long dailyFood, LocalDate startDate, LocalDate endDate, Long pigPenId) {
        this.id = id;
        this.feedType = feedType;
        this.dailyFood = dailyFood;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }
}

