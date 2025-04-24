package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService implements IAnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public void save(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public List<Animal> search(String name, String healthStatus, String raisingStatus,
                               LocalDate entryDateFrom, LocalDate entryDateTo, Long penId) {
        List<Animal> animals = animalRepository.findAll();

        // Lọc theo các tiêu chí
        return animals.stream()
                .filter(animal -> name == null || animal.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(animal -> healthStatus == null ||
                        animal.getHealthStatus().name().equalsIgnoreCase(healthStatus))
                .filter(animal -> raisingStatus == null ||
                        animal.getRaisingStatus().name().equalsIgnoreCase(raisingStatus))
                .filter(animal -> entryDateFrom == null ||
                        animal.getEntryDate() == null ||
                        !animal.getEntryDate().isBefore(entryDateFrom))
                .filter(animal -> entryDateTo == null ||
                        animal.getEntryDate() == null ||
                        !animal.getEntryDate().isAfter(entryDateTo))
                .filter(animal -> penId == null ||
                        (animal.getPigPen() != null && animal.getPigPen().getPenId().equals(penId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> findByPenId(Long penId) {
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getPigPen() != null &&
                        animal.getPigPen().getPenId().equals(penId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> findByHealthStatus(String healthStatus) {
        Animal.HealthStatus status = Animal.HealthStatus.valueOf(healthStatus.toUpperCase());
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getHealthStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> findByRaisingStatus(String raisingStatus) {
        Animal.RaisingStatus status = Animal.RaisingStatus.valueOf(raisingStatus.toUpperCase());
        return animalRepository.findAll().stream()
                .filter(animal -> animal.getRaisingStatus() == status)
                .collect(Collectors.toList());
    }
}