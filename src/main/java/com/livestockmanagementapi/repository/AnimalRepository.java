package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    // Tìm kiếm theo trạng thái sức khỏe
    List<Animal> findByHealthStatus(Animal.HealthStatus healthStatus);

    // Tìm kiếm theo trạng thái nuôi
    List<Animal> findByRaisingStatus(Animal.RaisingStatus raisingStatus);

    // Tìm kiếm theo cả hai trạng thái
    List<Animal> findByHealthStatusAndRaisingStatus(
            Animal.HealthStatus healthStatus,
            Animal.RaisingStatus raisingStatus);

    // Tìm kiếm theo tên chứa chuỗi (không phân biệt hoa thường)
    List<Animal> findByNameContainingIgnoreCase(String name);
}