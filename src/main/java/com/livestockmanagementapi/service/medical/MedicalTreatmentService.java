package com.livestockmanagementapi.service.medical;

import com.livestockmanagementapi.model.MedicalTreatment;
import com.livestockmanagementapi.repository.MedicalTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalTreatmentService {
    @Autowired
    private MedicalTreatmentRepository repository;

    public MedicalTreatment save(MedicalTreatment medicalTreatment) {
        return repository.save(medicalTreatment);
    }

    public Optional<MedicalTreatment> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<MedicalTreatment> search(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
