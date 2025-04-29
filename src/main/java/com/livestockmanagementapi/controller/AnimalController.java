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
import java.util.stream.Collectors;

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

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable("id") Long id) {
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
            // Nếu raisingStatus là EXPORTED thì không kiểm tra chuồng
            PigPen pen = null;
            if (!"EXPORTED".equalsIgnoreCase(request.getRaisingStatus())) {
                Optional<PigPen> pigPen = pigPenService.findById(request.getPenId());
                if (pigPen.isEmpty()) {
                    return ResponseEntity.badRequest().body("Chuồng nuôi không tồn tại");
                }
                pen = pigPen.get();
            }

            // Tạo đối tượng Animal từ request
            Animal animal = new Animal();
            animal.setName(request.getName());
            animal.setEntryDate(request.getEntryDate());
            animal.setExitDate(request.getExitDate());
            animal.setHealthStatus(Animal.HealthStatus.valueOf(request.getHealthStatus()));
            animal.setRaisingStatus(Animal.RaisingStatus.valueOf(request.getRaisingStatus()));
            animal.setWeight(request.getWeight());
            animal.setPigPen(pen); // Nếu exported thì null
            animal.setQuantity(request.getQuantity());

            // Cập nhật số lượng trong pigPen nếu không phải exported
            if (pen != null) {
                pen.setQuantity(pen.getQuantity() + request.getQuantity());
                pigPenService.save(pen);
            }

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

            Animal animal = existing.get();
            Long originalPenId = animal.getPigPen() != null ? animal.getPigPen().getPenId() : null;
            Integer originalQuantity = animal.getQuantity() != null ? animal.getQuantity() : 0;

            animal.setName(request.getName());
            animal.setEntryDate(request.getEntryDate());
            animal.setExitDate(request.getExitDate());
            animal.setHealthStatus(Animal.HealthStatus.valueOf(request.getHealthStatus()));
            animal.setRaisingStatus(Animal.RaisingStatus.valueOf(request.getRaisingStatus()));
            animal.setWeight(request.getWeight());

            // Nếu raisingStatus là EXPORTED thì bỏ qua xử lý chuồng
            if ("EXPORTED".equalsIgnoreCase(request.getRaisingStatus())) {
                animal.setPigPen(null);
            } else {
                // Kiểm tra pigPen tồn tại
                Optional<PigPen> pigPen = pigPenService.findById(request.getPenId());
                if (pigPen.isEmpty()) {
                    return ResponseEntity.badRequest().body("Chuồng nuôi không tồn tại");
                }
                PigPen newPen = pigPen.get();

                // Nếu thay đổi chuồng nuôi, cập nhật số lượng ở cả hai chuồng
                if (!request.getPenId().equals(originalPenId)) {
                    // Giảm số lượng ở chuồng cũ
                    if (originalPenId != null) {
                        Optional<PigPen> oldPen = pigPenService.findById(originalPenId);
                        if (oldPen.isPresent()) {
                            PigPen oldPigPen = oldPen.get();
                            oldPigPen.setQuantity(oldPigPen.getQuantity() - originalQuantity);
                            pigPenService.save(oldPigPen);
                        }
                    }

                    // Tăng số lượng ở chuồng mới
                    newPen.setQuantity(newPen.getQuantity() + request.getQuantity());
                    pigPenService.save(newPen);
                    animal.setPigPen(newPen);
                }
                // Nếu vẫn ở cùng một chuồng nhưng số lượng đã thay đổi
                else if (!request.getQuantity().equals(originalQuantity)) {
                    PigPen currentPen = newPen;
                    int quantityDifference = request.getQuantity() - originalQuantity;
                    currentPen.setQuantity(currentPen.getQuantity() + quantityDifference);
                    pigPenService.save(currentPen);
                    animal.setPigPen(currentPen);
                }
                else {
                    // Giữ nguyên chuồng và không thay đổi số lượng
                    animal.setPigPen(newPen);
                }
            }

            // Cập nhật số lượng của animal sau khi đã xử lý logic chuồng
            animal.setQuantity(request.getQuantity());

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
                    // Giảm số lượng theo số lượng cá thể thực tế
                    int quantity = animalToDelete.getQuantity() != null ? animalToDelete.getQuantity() : 1;
                    pen.setQuantity(pen.getQuantity() - quantity);
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
            @RequestParam(required = false) String healthStatus,
            @RequestParam(required = false) String raisingStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateTo,
            @RequestParam(required = false) Long penId) {
        try {
            return ResponseEntity.ok(animalService.search(name, healthStatus, raisingStatus, entryDateFrom, entryDateTo, penId));
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

    @GetMapping("/health-status/{healthStatus}")
    public ResponseEntity<?> getAnimalsByHealthStatus(@PathVariable String healthStatus) {
        try {
            return ResponseEntity.ok(animalService.findByHealthStatus(healthStatus));
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/raising-status/{raisingStatus}")
    public ResponseEntity<?> getAnimalsByRaisingStatus(@PathVariable String raisingStatus) {
        try {
            return ResponseEntity.ok(animalService.findByRaisingStatus(raisingStatus));
        } catch (Exception e) {
            e.printStackTrace(); // In chi tiết lỗi vào log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get all animals with EXPORTED status
     * @return List of animals that have been exported
     */
    @GetMapping("/exported")
    public ResponseEntity<?> getExportedAnimals() {
        try {
            // Use the findByRaisingStatus method with "EXPORTED" status
            List<Animal> exportedAnimals = animalService.findByRaisingStatus("EXPORTED");
            return ResponseEntity.ok(exportedAnimals);
        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving exported animals: " + e.getMessage());
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

    /**
     * Xuất chuồng cá thể vật nuôi
     * - Cập nhật trạng thái thành EXPORTED
     * - Đặt ngày xuất thành ngày hiện tại
     * - Giảm số lượng trong chuồng
     */
    @PostMapping("/{id}/export")
    public ResponseEntity<?> exportAnimal(@PathVariable Long id) {
        try {
            Optional<Animal> existing = animalService.findById(id);
            if (existing.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Animal animal = existing.get();

            // Đặt trạng thái là đã xuất
            animal.setRaisingStatus(Animal.RaisingStatus.EXPORTED);

            // Đặt ngày xuất là ngày hiện tại
            animal.setExitDate(LocalDate.now());

            // Cập nhật số lượng trong chuồng
            if (animal.getPigPen() != null) {
                PigPen pen = animal.getPigPen();
                // Giảm số lượng chuồng theo số lượng cá thể
                int quantity = animal.getQuantity() != null ? animal.getQuantity() : 1;
                if (pen.getQuantity() >= quantity) {
                    pen.setQuantity(pen.getQuantity() - quantity);
                    pigPenService.save(pen);
                }
            }

            // Lưu cá thể đã cập nhật
            animalService.save(animal);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi chi tiết
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xuất chuồng: " + e.getMessage());
        }
    }

    @GetMapping("/exported/search")
    public ResponseEntity<?> searchExportedAnimals(
            @RequestParam(required = false) Long pigId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateTo) {
        try {
            List<Animal> exportedAnimals = animalService.findByRaisingStatus("EXPORTED");

            // Lọc theo ID nếu có
            if (pigId != null) {
                exportedAnimals = exportedAnimals.stream()
                        .filter(animal -> animal.getPigId().equals(pigId))
                        .collect(Collectors.toList());
            }

            // Lọc theo ngày nhập
            if (entryDateFrom != null || entryDateTo != null) {
                exportedAnimals = exportedAnimals.stream()
                        .filter(animal -> {
                            if (entryDateFrom != null && animal.getEntryDate().isBefore(entryDateFrom)) {
                                return false;
                            }
                            if (entryDateTo != null && animal.getEntryDate().isAfter(entryDateTo)) {
                                return false;
                            }
                            return true;
                        })
                        .collect(Collectors.toList());
            }

            return ResponseEntity.ok(exportedAnimals);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching exported animals: " + e.getMessage());
        }
    }

}