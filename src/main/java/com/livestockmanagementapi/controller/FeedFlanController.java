package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO;
import com.livestockmanagementapi.model.dto.FeedPlan.FeedPlanRequest;
import com.livestockmanagementapi.service.feedFlan.FeedPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plan")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FeedFlanController {

    private final FeedPlanService feedPlanService;

    @PostMapping("/")
    public ResponseEntity<Void> createFeedPlan(@RequestBody FeedPlanRequest request) {
        feedPlanService.createFeedPlan(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFeedPlan(@PathVariable Long id,
                                               @RequestBody FeedPlanRequest request) {
        request.setId(id); // gán id từ URL vào DTO
        feedPlanService.saveOrUpdateFeedPlan(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<List<DailyFeedSummaryDTO>> getDailyFeedSummary() {
        List<DailyFeedSummaryDTO> summaries = feedPlanService.getDailyFeedSummary();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedPlan> getFeedPlanById(@PathVariable Long id) {
        Optional<FeedPlan> feedPlan = feedPlanService.findById(id);
        return feedPlan.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
