package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAnimalService extends IGenericService<Animal> {

    void save(Animal animal);

    /**
     * Tìm kiếm động vật theo nhiều tiêu chí
     */
    List<Animal> search(String name, String healthStatus, String raisingStatus,
                        LocalDate entryDateFrom, LocalDate entryDateTo, Long penId);

    /**
     * Tìm danh sách động vật theo ID chuồng
     */
    List<Animal> findByPenId(Long penId);

    /**
     * Tìm danh sách động vật theo trạng thái sức khỏe
     */
    List<Animal> findByHealthStatus(String healthStatus);

    /**
     * Tìm danh sách động vật theo trạng thái nuôi
     */
    List<Animal> findByRaisingStatus(String raisingStatus);
}