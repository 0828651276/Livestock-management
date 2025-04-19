package com.livestockmanagementapi.service.feedFlan;

import com.livestockmanagementapi.model.FeedBatch;
import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO;
import com.livestockmanagementapi.model.dto.FeedPlan.FeedPlanRequest;
import com.livestockmanagementapi.repository.FeedBatchRepository;
import com.livestockmanagementapi.repository.FeedPlanRepository;
import com.livestockmanagementapi.repository.PigPenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedPlanService implements IFeedPlanService {
    private final FeedPlanRepository feedPlanRepository;
    private final PigPenRepository pigPenRepository;
    private final FeedBatchRepository feedBatchRepository;

    public void createFeedPlan(FeedPlanRequest request) {
        FeedPlan plan = new FeedPlan();
        plan.setFeedType(request.getFeedType());
        plan.setDailyFood(request.getDailyFood());
        plan.setUnit(request.getUnit());
        plan.setStartDate(request.getStartDate());
        plan.setEndDate(request.getEndDate());

        if (request.getFeedBatchId() != null) {
            FeedBatch batch = feedBatchRepository.findById(request.getFeedBatchId())
                    .orElseThrow(() -> new RuntimeException("Feed batch not found"));
            plan.setFeedBatch(batch);
        }

        if (request.getPigPenId() != null) {
            PigPen pen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Pig pen not found"));
            plan.setPigPen(pen);
        }

        feedPlanRepository.save(plan);
    }

    public void saveOrUpdateFeedPlan(FeedPlanRequest request) {
        FeedPlan plan;

        if (request.getId() != null) {
            plan = feedPlanRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Feed plan not found"));
        } else {
            plan = new FeedPlan();
        }

        plan.setFeedType(request.getFeedType());
        plan.setDailyFood(request.getDailyFood());
        plan.setUnit(request.getUnit());
        plan.setStartDate(request.getStartDate());
        plan.setEndDate(request.getEndDate());

        if (request.getFeedBatchId() != null) {
            FeedBatch batch = feedBatchRepository.findById(request.getFeedBatchId())
                    .orElseThrow(() -> new RuntimeException("Feed batch not found"));
            plan.setFeedBatch(batch);
        }

        if (request.getPigPenId() != null) {
            PigPen pen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Pig pen not found"));
            plan.setPigPen(pen);
        }

        feedPlanRepository.save(plan);
    }

    public List<DailyFeedSummaryDTO> getDailyFeedSummary() {
        return feedPlanRepository.getDailyFeedSummary(LocalDate.now());
    }

    public List<FeedPlan> getPlansByHerdCode(String herdCode) {
        return feedPlanRepository.findByHerdCode(herdCode);
    }

}
