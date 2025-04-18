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
    public List<Animal> search(String name, String status, LocalDate entryDateFrom, LocalDate entryDateTo, Long penId) {
        List<Animal> allAnimals = findAll();
        
        return allAnimals.stream()
                .filter(animal -> name == null || 
                        (animal.getName() != null && animal.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(animal -> status == null || 
                        (animal.getStatus() != null && animal.getStatus().equalsIgnoreCase(status)))
                .filter(animal -> entryDateFrom == null || 
                        (animal.getEntryDate() != null && !animal.getEntryDate().isBefore(entryDateFrom)))
                .filter(animal -> entryDateTo == null || 
                        (animal.getEntryDate() != null && !animal.getEntryDate().isAfter(entryDateTo)))
                .filter(animal -> penId == null || 
                        (animal.getPigPen() != null && animal.getPigPen().getPenId().equals(penId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> findByPenId(Long penId) {
        return findAll().stream()
                .filter(animal -> animal.getPigPen() != null && animal.getPigPen().getPenId().equals(penId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> findByStatus(String status) {
        return animalRepository.findByStatusContainingIgnoreCase(status);
    }
}
