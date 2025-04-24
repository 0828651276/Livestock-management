package com.livestockmanagementapi.service.feedWarehouse;

import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedRequest;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO;
import com.livestockmanagementapi.repository.FeedWarehouseRepository;
import com.livestockmanagementapi.repository.PigPenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedWarehouseService implements IFeedWarehouseService {

    private final FeedWarehouseRepository feedWarehouseRepository;
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

    // nhap thuc an
    public void importFeed(FeedRequest request) {
        FeedWarehouse feedWarehouse = new FeedWarehouse();
        feedWarehouse.setFeedType(request.getFeedType());
        feedWarehouse.setQuantity(request.getQuantity());
        feedWarehouse.setDate(request.getDate());
        feedWarehouse.setNote(request.getNote()); // ✅ Lưu ghi chú
        feedWarehouse.setTransactionType(FeedWarehouse.TransactionType.IMPORT);

        if (request.getPigPenId() != null) {
            PigPen pigPen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuồng heo"));
            feedWarehouse.setPigPen(pigPen);
        }

        feedWarehouseRepository.save(feedWarehouse);
    }
// xuat thuc an
    public void exportFeed(FeedRequest request) {
        FeedWarehouse feedWarehouse = new FeedWarehouse();
        feedWarehouse.setFeedType(request.getFeedType());
        feedWarehouse.setQuantity(request.getQuantity());
        feedWarehouse.setDate(request.getDate());
        feedWarehouse.setNote(request.getNote()); // ✅ Lưu ghi chú
        feedWarehouse.setTransactionType(FeedWarehouse.TransactionType.EXPORT);

        if (request.getPigPenId() != null) {
            PigPen pigPen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuồng heo"));
            feedWarehouse.setPigPen(pigPen);
        }

        feedWarehouseRepository.save(feedWarehouse);
    }

    //luu thong tin
    private void saveTransaction(FeedRequest request, FeedWarehouse.TransactionType type) {
        FeedWarehouse warehouse = new FeedWarehouse();
        warehouse.setFeedType(request.getFeedType());
        warehouse.setQuantity(request.getQuantity());
        warehouse.setDate(request.getDate());
        warehouse.setTransactionType(type);

        if (request.getPigPenId() != null) {
            PigPen pen = pigPenRepository.findById(request.getPigPenId())
                    .orElseThrow(() -> new RuntimeException("Pig pen not found"));
            warehouse.setPigPen(pen);
        }

        feedWarehouseRepository.save(warehouse);
    }

    //tim kiem
    public List<FeedInventoryDTO> searchFeedInventory(String keyword) {
        return feedWarehouseRepository.searchInventoryByFeedType(keyword);
    }

    public List<FeedWarehouse> getTransactionsByFeedType(String feedType) {
        return feedWarehouseRepository.findTransactionsByFeedType(feedType);
    }

    @Override
    public List<FeedWarehouse> filterTransactions(String feedType, FeedWarehouse.TransactionType transactionType, LocalDate startDate, LocalDate endDate) {
        return feedWarehouseRepository.filterTransactions(feedType, transactionType, startDate, endDate);
    }

    @Override
    public List<FeedWarehouse> getTransactionsByFilter(String feedType, String transactionType, LocalDate startDate, LocalDate endDate) {
        if (transactionType != null && transactionType.isBlank()) {
            transactionType = null;
        }
        return feedWarehouseRepository.findByFilter(feedType, transactionType, startDate, endDate);
    }
}
