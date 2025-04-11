package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByFullName(String fullName);
    boolean existsByEmployeeId(String employeeId);
}

