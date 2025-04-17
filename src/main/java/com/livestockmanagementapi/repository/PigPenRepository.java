package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.PigPen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigPenRepository extends JpaRepository<PigPen, Long> {
    List<PigPen> findByCaretakerEmployeeId(String employeeId);
}

