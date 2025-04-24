package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface   AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByStatusContainingIgnoreCase(String status); // ví dụ tìm kiếm theo status
    List<Animal> findByPigPenPenId(Long penId);
    // Add a new method to find animals with EXPORTED status
    List<Animal> findByStatusEquals(String status);
    List<Animal> findByStatusIn(List<String> statuses);
}

