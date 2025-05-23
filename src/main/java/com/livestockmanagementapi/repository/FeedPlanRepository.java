package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedPlanRepository extends JpaRepository<FeedPlan, Long> {
    List<FeedPlan> findByPigPen_NameContainingIgnoreCase(String name);

    @Query("SELECT new com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO(f.id, p.penId, p.name, f.feedType, SUM(f.dailyFood)) \n" +
            "FROM FeedPlan f \n" +
            "JOIN f.pigPen p \n" +
            "GROUP BY f.id, p.penId, p.name, f.feedType\n")
    List<DailyFeedSummaryDTO> getDailyFeedSummary(@Param("today")LocalDate now);

    List<FeedPlan> findByPigPen(PigPen pigPen);

//    @Query("SELECT f FROM FeedPlan f WHERE f.feedBatch.herdCode = :herdCode")
//    List<FeedPlan> findByHerdCode(@Param("herdCode") String herdCode);

}
