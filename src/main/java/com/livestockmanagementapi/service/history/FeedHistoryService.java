package com.livestockmanagementapi.service.history;

import com.livestockmanagementapi.model.FeedHistory;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryDTO;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryRequest;
import com.livestockmanagementapi.service.IGenericService;

import java.util.List;

public interface FeedHistoryService extends IGenericService<FeedHistory> {
    FeedHistory findById(String id);

    FeedHistoryDTO createFeedHistory(FeedHistoryRequest request);
    List<FeedHistoryDTO> getAllFeedHistories();
    List<FeedHistoryDTO> getFeedHistoriesByPigPenId(Long pigPenId);
} 