package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryDTO;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryRequest;
import com.livestockmanagementapi.service.history.FeedHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/feed-history")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FeedHistoryController {

    private final FeedHistoryService feedHistoryService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<FeedHistoryDTO> createFeedHistory(@RequestBody FeedHistoryRequest request) {
        return new ResponseEntity<>(feedHistoryService.createFeedHistory(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<FeedHistoryDTO> updateFeedHistory(
            @PathVariable Long id,
            @RequestBody FeedHistoryRequest request) {
        return ResponseEntity.ok(feedHistoryService.updateFeedHistory(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteFeedHistory(@PathVariable Long id) {
        feedHistoryService.deleteFeedHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<FeedHistoryDTO> getFeedHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(feedHistoryService.getFeedHistoryById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<FeedHistoryDTO>> getAllFeedHistories() {
        return ResponseEntity.ok(feedHistoryService.getAllFeedHistories());
    }

    @GetMapping("/pig-pen/{pigPenId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<FeedHistoryDTO>> getFeedHistoriesByPigPenId(@PathVariable Long pigPenId) {
        return ResponseEntity.ok(feedHistoryService.getFeedHistoriesByPigPenId(pigPenId));
    }

    @GetMapping("/animal/{animalId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<FeedHistoryDTO>> getFeedHistoriesByAnimalId(@PathVariable Long animalId) {
        return ResponseEntity.ok(feedHistoryService.getFeedHistoriesByAnimalId(animalId));
    }

    @GetMapping("/feed-plan/{feedPlanId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<FeedHistoryDTO>> getFeedHistoriesByFeedPlanId(@PathVariable Long feedPlanId) {
        return ResponseEntity.ok(feedHistoryService.getFeedHistoriesByFeedPlanId(feedPlanId));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<List<FeedHistoryDTO>> getFeedHistoriesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(feedHistoryService.getFeedHistoriesByDateRange(startDate, endDate));
    }
}
