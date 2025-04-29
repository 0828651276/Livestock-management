package com.livestockmanagementapi.service.vaccination;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.model.Vaccination;
import com.livestockmanagementapi.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Override
    public List<Vaccination> findAll() {
        return vaccinationRepository.findAll();
    }

    @Override
    public Optional<Vaccination> findById(Long id) {
        return vaccinationRepository.findById(id);
    }

    @Override
    public void save(Vaccination vaccination) {
        vaccinationRepository.save(vaccination);
    }

    @Override
    public void deleteById(Long id) {
        vaccinationRepository.deleteById(id);
    }


    @Override
    public Vaccination saveVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    @Override
    public List<Vaccination> findByAnimalId(Long pigId) {
        return vaccinationRepository.findByAnimal_PigId(pigId);
    }

    @Override
    public List<Vaccination> findByStatus(Vaccination.Status status) {
        return vaccinationRepository.findByStatus(status);
    }

    @Override
    public List<Vaccination> findByAnimalIdAndStatus(Long pigId, Vaccination.Status status) {
        return vaccinationRepository.findByAnimal_PigIdAndStatus(pigId, status);
    }
}
