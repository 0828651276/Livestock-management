package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.FeedBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBatchRepository extends JpaRepository<FeedBatch, Long> {
}
