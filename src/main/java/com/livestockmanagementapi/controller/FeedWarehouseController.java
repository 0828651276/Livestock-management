package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedRequest;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO;
import com.livestockmanagementapi.repository.FeedWarehouseRepository;
import com.livestockmanagementapi.service.feedWarehouse.FeedWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/feedwarehouse")
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class FeedWarehouseController {

    private final FeedWarehouseService feedWarehouseService;

    @GetMapping("")
    public List<FeedWarehouse> getAllFeedWarehouses() {
        return feedWarehouseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedWarehouse> getFeedWarehouseById(@PathVariable long id) {
        Optional<FeedWarehouse> feedWarehouse = feedWarehouseService.findById(id);
        if (feedWarehouse.isPresent()) {
            return ResponseEntity.ok(feedWarehouse.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

//    them moi thuc an
    @PostMapping("")
    public FeedWarehouse addFeedWarehouse(@RequestBody FeedWarehouse feedWarehouse) {
        feedWarehouseService.save(feedWarehouse);
        return feedWarehouse;
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedWarehouse> updateFeedWarehouse(@PathVariable long id, @RequestBody FeedWarehouse feedWarehouse) {
        Optional<FeedWarehouse> feedWarehouseOptional = feedWarehouseService.findById(id);
        if (feedWarehouseOptional.isPresent()) {
            feedWarehouse.setId(id);
            feedWarehouseService.save(feedWarehouse);
            return ResponseEntity.ok(feedWarehouse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

//    danh sách thức ăn trong kho, để biết còn bao nhiêu thức ăn
    @GetMapping("/inventory")
    public ResponseEntity<List<FeedInventoryDTO>> getFeedInventory() {
        return ResponseEntity.ok(feedWarehouseService.getCurrentFeedInventory());
    }

//    thêm thức ăn vào kho
    @PostMapping("/import")
    public ResponseEntity<Void> importFeed(@RequestBody FeedRequest request) {
        feedWarehouseService.importFeed(request);
        return ResponseEntity.ok().build();
    }

    //xuat thuc an ra khoi kho
    @PostMapping("/export")
    public ResponseEntity<Void> exportFeed(@RequestBody FeedRequest request) {
        feedWarehouseService.exportFeed(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FeedInventoryDTO>> searchFeedInventory(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(feedWarehouseService.searchFeedInventory(keyword));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<FeedWarehouse>> getTransactionsByFeedType(@RequestParam("feedType") String feedType) {
        return ResponseEntity.ok(feedWarehouseService.getTransactionsByFeedType(feedType));
    }


    @GetMapping("/filter")
    public ResponseEntity<List<FeedWarehouse>> filterTransactions(
            @RequestParam(required = false) String feedType,
            @RequestParam(required = false) FeedWarehouse.TransactionType transactionType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(feedWarehouseService.filterTransactions(feedType, transactionType, startDate, endDate));
    }

    @GetMapping("/{feedType}/transactions")
    public ResponseEntity<List<FeedWarehouse>> getTransactions(
            @PathVariable String feedType,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<FeedWarehouse> transactions = feedWarehouseService.getTransactionsByFilter(
                feedType, transactionType, startDate, endDate
        );
        return ResponseEntity.ok(transactions);
    }
}

