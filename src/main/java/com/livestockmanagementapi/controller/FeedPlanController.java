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
public class FeedPlanController {

    private final FeedPlanService feedPlanService;

    @PostMapping
    public ResponseEntity<Void> createFeedPlan(@RequestBody FeedPlanRequest request) {
        feedPlanService.createFeedPlan(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFeedPlan(@PathVariable Long id, @RequestBody FeedPlanRequest request) {
        if (request.getId() == null) {
            request.setId(id); // Đảm bảo id luôn được gán vào request
        }
        feedPlanService.saveOrUpdateFeedPlan(request);
        return ResponseEntity.ok().build(); // Trả về OK sau khi cập nhật
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

    @GetMapping("/search")
    public ResponseEntity<List<FeedPlan>> searchFeedPlansByPenName(@RequestParam(value = "penName") String penName) {
        List<FeedPlan> plans = feedPlanService.searchByPenName(penName);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/pen/{penId}")
    public ResponseEntity<List<FeedPlan>> getFeedPlansByPenId(@PathVariable Long penId) {
        List<FeedPlan> plans = feedPlanService.findByPenId(penId);
        return ResponseEntity.ok(plans);
    }
}
