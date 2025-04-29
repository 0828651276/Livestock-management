package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.FeedHistory;
import com.livestockmanagementapi.model.PigPen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FeedHistoryRepository extends JpaRepository<FeedHistory, Long> {
    
    List<FeedHistory> findByPigPen(PigPen pigPen);
    
    List<FeedHistory> findByAnimal(Animal animal);
    
    List<FeedHistory> findByFeedingTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT fh FROM FeedHistory fh WHERE fh.pigPen.id = :pigPenId")
    List<FeedHistory> findByPigPenId(Long pigPenId);
    
    @Query("SELECT fh FROM FeedHistory fh WHERE fh.animal.id = :animalId")
    List<FeedHistory> findByAnimalId(Long animalId);
    
    @Query("SELECT fh FROM FeedHistory fh WHERE fh.feedPlan.id = :feedPlanId")
    List<FeedHistory> findByFeedPlanId(Long feedPlanId);
}
