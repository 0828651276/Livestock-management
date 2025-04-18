package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.animal.AnimalRequest;
import com.livestockmanagementapi.service.animal.IAnimalService;
import com.livestockmanagementapi.service.pigpen.IPigPenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin("*")
@Validated
public class AnimalController {

    @Autowired
    private IAnimalService animalService;

    @Autowired
    private IPigPenService pigPenService;

    @GetMapping
    public ResponseEntity<List<Animal>> findAll() {
        try {
            return ResponseEntity.ok(animalService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        try {
            return animalService.findById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addAnimal(@Valid @RequestBody AnimalRequest request) {
        try {
            // Kiểm tra pigPen tồn tại
            Optional<PigPen> pigPen = pigPenService.findById(request.getPenId());
            if (pigPen.isEmpty()) {
                return ResponseEntity.badRequest().body("Chuồng nuôi không tồn tại");
            }

            // Tạo đối tượng Animal từ request
            Animal animal = new Animal();
            animal.setName(request.getName());
            animal.setEntryDate(request.getEntryDate());
            animal.setExitDate(request.getExitDate());
            animal.setStatus(request.getStatus());
            animal.setWeight(request.getWeight());
            animal.setPigPen(pigPen.get());

            // Cập nhật số lượng trong pigPen
            PigPen pen = pigPen.get();
            pen.setQuantity(pen.getQuantity() + 1);
            pigPenService.save(pen);

            animalService.save(animal);
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalRequest request) {
        try {
            Optional<Animal> existing = animalService.findById(id);
            if (existing.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Kiểm tra pigPen tồn tại
            Optional<PigPen> pigPen = pigPenService.findById(request.getPenId());
            if (pigPen.isEmpty()) {
                return ResponseEntity.badRequest().body("Chuồng nuôi không tồn tại");
            }

            // Cập nhật thông tin animal
            Animal animal = existing.get();
            animal.setName(request.getName());
            animal.setEntryDate(request.getEntryDate());
            animal.setExitDate(request.getExitDate());
            animal.setStatus(request.getStatus());
            animal.setWeight(request.getWeight());

            // Nếu thay đổi pigPen, cập nhật số lượng
            if (!animal.getPigPen().getPenId().equals(request.getPenId())) {
                // Giảm số lượng ở pen cũ
                PigPen oldPen = animal.getPigPen();
                oldPen.setQuantity(oldPen.getQuantity() - 1);
                pigPenService.save(oldPen);

                // Tăng số lượng ở pen mới
                PigPen newPen = pigPen.get();
                newPen.setQuantity(newPen.getQuantity() + 1);
                pigPenService.save(newPen);

                animal.setPigPen(newPen);
            }

            animalService.save(animal);
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long id) {
        try {
            Optional<Animal> animal = animalService.findById(id);
            if (animal.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Cập nhật số lượng trong pigPen
            Animal animalToDelete = animal.get();
            if (animalToDelete.getPigPen() != null) {
                PigPen pen = animalToDelete.getPigPen();
                pen.setQuantity(pen.getQuantity() - 1);
                pigPenService.save(pen);
            }

            animalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateTo,
            @RequestParam(required = false) Long penId) {
        try {
            return ResponseEntity.ok(animalService.search(name, status, entryDateFrom, entryDateTo, penId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/pen/{penId}")
    public ResponseEntity<?> getAnimalsByPenId(@PathVariable Long penId) {
        try {
            return ResponseEntity.ok(animalService.findByPenId(penId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAnimalsByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(animalService.findByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}