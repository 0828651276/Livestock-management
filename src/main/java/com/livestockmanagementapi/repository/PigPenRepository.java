package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.PigPen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PigPenRepository extends JpaRepository<PigPen, Long> {
    // Tìm chuồng theo người chăm sóc đơn lẻ (tương thích ngược)
    List<PigPen> findByCaretakerEmployeeId(String employeeId);

    // Tìm chuồng theo bất kỳ người chăm sóc nào trong danh sách caretakers
    @Query("SELECT p FROM PigPen p JOIN p.caretakers c WHERE c.employeeId = :employeeId")
    List<PigPen> findByAnyCaretakerEmployeeId(@Param("employeeId") String employeeId);

    // Tìm chuồng theo từ khóa tên
    List<PigPen> findByNameContainingIgnoreCase(String name);
}