package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Medical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRepository extends JpaRepository<Medical, Long> {
    /**
     * Find medical records by associated Animal pigId
     */
    List<Medical> findByAnimal_PigId(Long pigId);

    List<Medical> findByTreatmentDate(LocalDate treatmentDate);

    List<Medical> findByTreatmentDateLessThanEqual(LocalDate date);}