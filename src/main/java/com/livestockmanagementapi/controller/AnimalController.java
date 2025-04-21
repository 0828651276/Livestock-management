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
import java.util.*;

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
            animal.setQuantity(request.getQuantity());

            // Cập nhật số lượng trong pigPen
            PigPen pen = pigPen.get();
            pen.setQuantity(pen.getQuantity() + request.getQuantity());
            pigPenService.save(pen);

            animalService.save(animal);
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
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
            Long originalPenId = animal.getPigPen() != null ? animal.getPigPen().getPenId() : null;

            animal.setName(request.getName());
            animal.setEntryDate(request.getEntryDate());
            animal.setExitDate(request.getExitDate());
            animal.setStatus(request.getStatus());
            animal.setWeight(request.getWeight());
            animal.setQuantity(request.getQuantity());

            // Nếu thay đổi chuồng nuôi, cập nhật số lượng ở cả hai chuồng
            if (!request.getPenId().equals(originalPenId)) {
                // Giảm số lượng ở chuồng cũ
                if (originalPenId != null) {
                    Optional<PigPen> oldPen = pigPenService.findById(originalPenId);
                    if (oldPen.isPresent()) {
                        PigPen oldPigPen = oldPen.get();
                        oldPigPen.setQuantity(oldPigPen.getQuantity() - animal.getQuantity());
                        pigPenService.save(oldPigPen);
                    }
                }

                // Tăng số lượng ở chuồng mới
                PigPen newPen = pigPen.get();
                newPen.setQuantity(newPen.getQuantity() + request.getQuantity());
                pigPenService.save(newPen);
                animal.setPigPen(newPen);
            }

            animalService.save(animal);
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi cập nhật: " + e.getMessage());
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
                if (pen.getQuantity() > 0) {
                    pen.setQuantity(pen.getQuantity() - 1);
                    pigPenService.save(pen);
                }
            }

            animalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
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
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/pen/{penId}")
    public ResponseEntity<?> getAnimalsByPenId(@PathVariable Long penId) {
        try {
            return ResponseEntity.ok(animalService.findByPenId(penId));
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAnimalsByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(animalService.findByStatus(status));
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getAnimalsByEmployeeId(@PathVariable String employeeId) {
        try {
            // Lấy danh sách chuồng do nhân viên chăm sóc
            List<PigPen> employeePens = pigPenService.findByEmployeeId(employeeId);

            if (employeePens.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }

            // Lấy động vật từ các chuồng và loại bỏ trùng lặp
            Set<Animal> uniqueAnimals = new HashSet<>();
            for (PigPen pen : employeePens) {
                List<Animal> penAnimals = animalService.findByPenId(pen.getPenId());
                uniqueAnimals.addAll(penAnimals);
            }

            return ResponseEntity.ok(new ArrayList<>(uniqueAnimals));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/empty")
    public ResponseEntity<List<PigPen>> getEmptyPens() {
        try {
            List<PigPen> emptyPens = pigPenService.findEmptyPens();
            return ResponseEntity.ok(emptyPens);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}



