package com.livestockmanagementapi.service;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService implements com.livestockmanagementapi.service.IAnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> getAnimalById(String pigId) {
        return animalRepository.findById(pigId);
    }

    @Override
    public List<Animal> searchByStatus(String status) {
        return animalRepository.findByStatusContainingIgnoreCase(status);
    }

    @Override
    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal updateAnimal(String pigId, Animal updatedAnimal) {
        return animalRepository.findById(pigId).map(animal -> {
            animal.setPigPen(updatedAnimal.getPigPen());
            animal.setEntryDate(updatedAnimal.getEntryDate());
            animal.setExitDate(updatedAnimal.getExitDate());
            animal.setStatus(updatedAnimal.getStatus());
            animal.setWeight(updatedAnimal.getWeight());
            return animalRepository.save(animal);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy Animal với pigId: " + pigId));
    }

    @Override
    public void deleteAnimal(String pigId) {
        animalRepository.deleteById(pigId);
    }
}
