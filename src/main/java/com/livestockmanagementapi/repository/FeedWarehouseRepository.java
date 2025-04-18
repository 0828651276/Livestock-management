package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedWarehouseRepository extends JpaRepository<FeedWarehouse, Long> {

    @Query("SELECT new com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO(f.feedType, f.unit, " +
            "SUM(CASE WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.IMPORT THEN f.quantity " +
            "         WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.EXPORT THEN -f.quantity ELSE 0 END)) " +
            "FROM FeedWarehouse f GROUP BY f.feedType, f.unit")
    List<FeedInventoryDTO> getFeedInventorySummary();
}
