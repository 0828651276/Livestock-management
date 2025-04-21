package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FeedPlanRepository extends JpaRepository<FeedPlan, Long> {

    @Query("SELECT new com.livestockmanagementapi.model.dto.FeedPlan.DailyFeedSummaryDTO(" +
            "p.penId, p.name, f.feedType, SUM(f.dailyFood)) " +
            "FROM FeedPlan f JOIN f.pigPen p " +
            "WHERE :today BETWEEN f.startDate AND f.endDate " +
            "GROUP BY p.penId, p.name, f.feedType, f.unit")
    List<DailyFeedSummaryDTO> getDailyFeedSummary(@Param("today") LocalDate today);

//    @Query("SELECT f FROM FeedPlan f WHERE f.feedBatch.herdCode = :herdCode")
//    List<FeedPlan> findByHerdCode(@Param("herdCode") String herdCode);

}
