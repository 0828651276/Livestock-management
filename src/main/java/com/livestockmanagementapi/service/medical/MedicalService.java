package com.livestockmanagementapi.service.medical;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.repository.MedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalService implements IMedicalService {

    @Autowired
    private MedicalRepository medicalRepository;

    @Override
    public List<Medical> findAll() {
        return medicalRepository.findAll();
    }

    @Override
    public Optional<Medical> findById(Long id) {
        return medicalRepository.findById(id);
    }

    @Override
    public void save(Medical medical) {
        medicalRepository.save(medical);
    }

    @Override
    public void deleteById(Long id) {
        medicalRepository.deleteById(id);
    }

    @Override
    public List<Medical> findByAnimalId(Long pigId) {
        return medicalRepository.findByAnimal_PigId(pigId);
    }

    @Override
    public List<Medical> findByTreatmentDateLessThanEqual(LocalDate date) {
        return medicalRepository.findByTreatmentDateLessThanEqual(date);
    }
}