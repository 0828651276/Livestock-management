package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    List<Vaccination> findAllByOrderByDate();
}
