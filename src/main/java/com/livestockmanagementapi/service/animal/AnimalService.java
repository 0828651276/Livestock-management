package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService implements IAnimalService {

    @Autowired
    private AnimalRepository animalRepository;


    @Override
    public List findAll() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Animal animal) {
        animalRepository.save(animal);
    }
}
