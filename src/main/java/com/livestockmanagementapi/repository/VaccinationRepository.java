package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    List<Vaccination> findAllByOrderByDate();
    List<Vaccination> findByAnimal_PigId(Long pigId);
    List<Vaccination> findByStatus(Vaccination.Status status);
    List<Vaccination> findByAnimal_PigIdAndStatus(Long pigId, Vaccination.Status status);
}
