package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.service.animal.IAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin("*")
public class AnimalController {

    @Autowired
    private IAnimalService animalService;

    @GetMapping
    public List<Animal> findAll() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable Long id) {
        return animalService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Animal với pigId: " + id));
    }

    @PostMapping
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {

        animalService.save(animal);
        return ResponseEntity.ok(animal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        Optional<Animal> existing = animalService.findById(id);
        if (existing.isPresent()) {
            updatedAnimal.setPigId(id);
            animalService.save(updatedAnimal);
            return ResponseEntity.ok(updatedAnimal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id){
        Optional<Animal> animal = animalService.findById(id);
        if (animal.isPresent()) {
            animalService.deleteById(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.noContent().build();
    }
}