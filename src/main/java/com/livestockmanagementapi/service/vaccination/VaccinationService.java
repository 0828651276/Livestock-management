package com.livestockmanagementapi.service.vaccination;

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
        return vaccinationRepository.findAllByOrderByDate();
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
}
