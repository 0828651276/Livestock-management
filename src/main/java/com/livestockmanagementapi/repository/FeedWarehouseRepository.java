package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedWarehouseRepository extends JpaRepository<FeedWarehouse, Long> {

    @Query("SELECT new com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO(f.feedType, " +
            "SUM(CASE WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.IMPORT THEN f.quantity " +
            "         WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.EXPORT THEN -f.quantity ELSE 0 END), " +
            "f.note) " +
            "FROM FeedWarehouse f " +
            "GROUP BY f.feedType, f.note")
    List<FeedInventoryDTO> getFeedInventorySummary();


    @Query("SELECT new com.livestockmanagementapi.model.dto.feedWarehouse.FeedInventoryDTO(f.feedType, " +
            "SUM(CASE WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.IMPORT THEN f.quantity " +
            "         WHEN f.transactionType = com.livestockmanagementapi.model.FeedWarehouse.TransactionType.EXPORT THEN -f.quantity ELSE 0 END), " +
            "f.note) " +
            "FROM FeedWarehouse f " +
            "WHERE LOWER(f.feedType) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "GROUP BY f.feedType, f.note")
    List<FeedInventoryDTO> searchInventoryByFeedType(@Param("keyword") String keyword);

    @Query("SELECT fw FROM FeedWarehouse fw WHERE LOWER(fw.feedType) = LOWER(:feedType) ORDER BY fw.date DESC")
    List<FeedWarehouse> findTransactionsByFeedType(@Param("feedType") String feedType);

    @Query("""
    SELECT fw FROM FeedWarehouse fw
    WHERE (:feedType IS NULL OR LOWER(fw.feedType) = LOWER(:feedType))
      AND (:transactionType IS NULL OR fw.transactionType = :transactionType)
      AND (:startDate IS NULL OR fw.date >= :startDate)
      AND (:endDate IS NULL OR fw.date <= :endDate)
    ORDER BY fw.date DESC
""")
    List<FeedWarehouse> filterTransactions(
            @Param("feedType") String feedType,
            @Param("transactionType") FeedWarehouse.TransactionType transactionType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT t FROM FeedWarehouse t " +
            "WHERE t.feedType = :feedType " +
            "AND (:transactionType IS NULL OR t.transactionType = :transactionType) " +
            "AND (:startDate IS NULL OR t.date >= :startDate) " +
            "AND (:endDate IS NULL OR t.date <= :endDate)")
    List<FeedWarehouse> findByFilter(
            @Param("feedType") String feedType,
            @Param("transactionType") String transactionType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
