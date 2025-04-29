package com.livestockmanagementapi.service.history;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.FeedHistory;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryDTO;
import com.livestockmanagementapi.model.dto.feedhistory.FeedHistoryRequest;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedHistoryService extends IGenericService<FeedHistory> {
    
    FeedHistoryDTO createFeedHistory(FeedHistoryRequest request);
    
    FeedHistoryDTO updateFeedHistory(Long id, FeedHistoryRequest request);
    
    void deleteFeedHistory(Long id);
    
    FeedHistoryDTO getFeedHistoryById(Long id);
    
    List<FeedHistoryDTO> getAllFeedHistories();
    
    List<FeedHistoryDTO> getFeedHistoriesByPigPen(PigPen pigPen);
    
    List<FeedHistoryDTO> getFeedHistoriesByPigPenId(Long pigPenId);
    
    List<FeedHistoryDTO> getFeedHistoriesByAnimal(Animal animal);
    
    List<FeedHistoryDTO> getFeedHistoriesByAnimalId(Long animalId);
    
    List<FeedHistoryDTO> getFeedHistoriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<FeedHistoryDTO> getFeedHistoriesByFeedPlanId(Long feedPlanId);
}
