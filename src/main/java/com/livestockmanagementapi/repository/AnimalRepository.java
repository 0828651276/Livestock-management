package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, String> {
    List<Animal> findByStatusContainingIgnoreCase(String status); // ví dụ tìm kiếm theo status
}

