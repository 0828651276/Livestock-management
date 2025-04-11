package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);
    // Tìm theo tên gần đúng (không phân biệt hoa thường)
    List<Employee> findByFullNameContainingIgnoreCase(String name);

    // Tìm theo id gần đúng
    List<Employee> findByEmployeeIdContainingIgnoreCase(String id);

    // Tìm theo cả id và tên gần đúng
    List<Employee> findByEmployeeIdContainingIgnoreCaseAndFullNameContainingIgnoreCase(String id, String name);
}

