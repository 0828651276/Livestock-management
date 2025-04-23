package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.MedicalTreatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicalTreatmentRepository extends JpaRepository<MedicalTreatment, Long>, JpaSpecificationExecutor<MedicalTreatment> {
}
