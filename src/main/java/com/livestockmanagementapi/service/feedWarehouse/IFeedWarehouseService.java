package com.livestockmanagementapi.service.feedWarehouse;

import com.livestockmanagementapi.model.FeedWarehouse;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDate;
import java.util.List;

public interface IFeedWarehouseService extends IGenericService <FeedWarehouse> {
    List<FeedWarehouse> getTransactionsByFeedType(String feedType);
    List<FeedWarehouse> filterTransactions(String feedType, FeedWarehouse.TransactionType transactionType, LocalDate startDate, LocalDate endDate);
    List<FeedWarehouse> getTransactionsByFilter(String feedType, String transactionType, LocalDate startDate, LocalDate endDate);

}
