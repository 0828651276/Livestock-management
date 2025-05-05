package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.FeedHistory;
import com.livestockmanagementapi.model.PigPen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedHistoryRepository extends JpaRepository<FeedHistory, Long> {
    @Query("SELECT fh FROM FeedHistory fh WHERE fh.pigPen.penId = :pigPenId")
    List<FeedHistory> findByPigPenId(@Param("pigPenId") Long pigPenId);
} 