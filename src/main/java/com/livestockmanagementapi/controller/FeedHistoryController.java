package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryDTO;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryRequest;
import com.livestockmanagementapi.service.history.FeedHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/feed-history")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FeedHistoryController {
    private final FeedHistoryService feedHistoryService;

    @PostMapping
    public ResponseEntity<?> createFeedHistory(@RequestBody FeedHistoryRequest request) {
        try {
            log.info("Received feed history request: {}", request);
            FeedHistoryDTO createdHistory = feedHistoryService.createFeedHistory(request);
            log.info("Successfully created feed history: {}", createdHistory);
            return ResponseEntity.ok(createdHistory);
        } catch (Exception e) {
            log.error("Error creating feed history: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error creating feed history: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FeedHistoryDTO>> getAllFeedHistories() {
        List<FeedHistoryDTO> histories = feedHistoryService.getAllFeedHistories();
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/pigpens/{pigPenId}")
    public ResponseEntity<List<FeedHistoryDTO>> getFeedHistoriesByPigPenId(@PathVariable Long pigPenId) {
        List<FeedHistoryDTO> histories = feedHistoryService.getFeedHistoriesByPigPenId(pigPenId);
        return ResponseEntity.ok(histories);
    }
}