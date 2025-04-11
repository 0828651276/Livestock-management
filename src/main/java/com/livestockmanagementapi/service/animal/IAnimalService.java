package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;

import java.util.List;
import java.util.Optional;

public interface IAnimalService {
    List<Animal> getAllAnimals();
    Optional<Animal> getAnimalById(String pigId);
    List<Animal> searchByStatus(String status);
    Animal addAnimal(Animal animal);
    Animal updateAnimal(String pigId, Animal animal);
    void deleteAnimal(String pigId);
}
