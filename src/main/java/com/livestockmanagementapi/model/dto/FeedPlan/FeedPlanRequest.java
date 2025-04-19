package com.livestockmanagementapi.model.dto.FeedPlan;

import java.time.LocalDate;

public class FeedPlanRequest {
    private Long id; // optional: nếu null thì là thêm mới, nếu có thì là cập nhật
    private String feedType;
    private Long dailyFood;
    private String unit;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long feedBatchId;
    private Long pigPenId;

    public FeedPlanRequest(Long id, String feedType, Long dailyFood, String unit, LocalDate startDate, LocalDate endDate, Long feedBatchId, Long pigPenId) {
        this.id = id;
        this.feedType = feedType;
        this.dailyFood = dailyFood;
        this.unit = unit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feedBatchId = feedBatchId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

