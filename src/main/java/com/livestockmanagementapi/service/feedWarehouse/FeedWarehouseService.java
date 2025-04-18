package com.livestockmanagementapi.service.feedWarehouse;

import com.livestockmanagementapi.model.FeedBatch;
import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedImportRequest;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO;
import com.livestockmanagementapi.repository.FeedBatchRepository;
import com.livestockmanagementapi.repository.FeedWarehouseRepository;
import com.livestockmanagementapi.repository.PigPenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedWarehouseService implements IFeedWarehouseService {

    private final FeedWarehouseRepository feedWarehouseRepository;
    private final FeedBatchRepository feedBatchRepository;
    private final PigPenRepository pigPenRepository;

    @Override
    public List<FeedWarehouse> findAll() {
        return feedWarehouseRepository.findAll();
    }

    @Override
    public Optional<FeedWarehouse> findById(Long id) {
        return feedWarehouseRepository.findById(id);
    }

    @Override
    public void save(FeedWarehouse feedWarehouse) {
        feedWarehouseRepository.save(feedWarehouse);
    }

    @Override
    public void deleteById(Long id) {
        feedWarehouseRepository.deleteById(id);
    }

    public List<FeedInventoryDTO> getCurrentFeedInventory() {
        return feedWarehouseRepository.getFeedInventorySummary();
    }

    public void importFeed(FeedImportRequest request) {
        FeedWarehouse warehouse = new FeedWarehouse();
        warehouse.setFeedType(request.getFeedType());
        warehouse.setQuantity(request.getQuantity());
        warehouse.setUnit(request.getUnit());
        warehouse.setDate(request.getDate());
        warehouse.setTransactionType(FeedWarehouse.TransactionType.IMPORT);

        if (request.getFeedBatchId() != null) {
            FeedBatch batch = feedBatchRepository.findById(request.getFeedBatchId())
                    .orElseThrow(() -> new RuntimeException("Feed batch not found"));
            warehouse.setFeedBatch(batch);
        }

        if (request.getPigPenId() != null) {
            PigPen pen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Pig pen not found"));
            warehouse.setPigPen(pen);
        }

        feedWarehouseRepository.save(warehouse);
    }
}
