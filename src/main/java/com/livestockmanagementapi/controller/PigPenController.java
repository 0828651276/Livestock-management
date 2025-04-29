package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.pigpen.PigPenWithAnimalDTO;
import com.livestockmanagementapi.repository.AnimalRepository;
import com.livestockmanagementapi.repository.EmployeeRepository;
import com.livestockmanagementapi.service.pigpen.IPigPenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pigpens")
@CrossOrigin("*")
public class PigPenController {

    @Autowired
    private IPigPenService pigPenService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<PigPen> getAllPigPens() {
        return pigPenService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PigPen> getPigPenById(@PathVariable Long id) {
        Optional<PigPen> pigPen = pigPenService.findById(id);
        return pigPen.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PigPen> addPigPen(@RequestBody PigPen pigPen) {
        // Chỉ xử lý caretakers (nhiều người)
        handleCaretakers(pigPen);
        pigPenService.save(pigPen);
        return ResponseEntity.ok(pigPen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PigPen> updatePigPen(@PathVariable Long id, @RequestBody PigPen updatedPigPen) {
        Optional<PigPen> existing = pigPenService.findById(id);
        if (existing.isPresent()) {
            updatedPigPen.setPenId(id);
            // Chỉ xử lý caretakers (nhiều người)
            handleCaretakers(updatedPigPen);
            pigPenService.save(updatedPigPen);
            return ResponseEntity.ok(updatedPigPen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * API để nhân viên thoát khỏi chuồng họ đang chăm sóc
     */
    @DeleteMapping("/{penId}/caretakers/{employeeId}")
    public ResponseEntity<?> removeCaretakerFromPen(
            @PathVariable Long penId,
            @PathVariable String employeeId) {
        try {
            // Kiểm tra chuồng có tồn tại không
            Optional<PigPen> pigPenOpt = pigPenService.findById(penId);
            if (pigPenOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy chuồng với ID: " + penId);
            }

            // Kiểm tra nhân viên có tồn tại không
            Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
            if (employeeOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy nhân viên với ID: " + employeeId);
            }

            PigPen pigPen = pigPenOpt.get();
            Employee employee = employeeOpt.get();

            // Kiểm tra xem nhân viên có phải là người chăm sóc của chuồng này không
            if (pigPen.getCaretakers() == null || !pigPen.getCaretakers().contains(employee)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Nhân viên không phải là người chăm sóc của chuồng này");
            }

            // Xóa nhân viên khỏi danh sách caretakers
            pigPen.getCaretakers().remove(employee);

            // Lưu chuồng đã cập nhật
            pigPenService.save(pigPen);

            return ResponseEntity.ok()
                    .body("Đã xóa nhân viên khỏi chuồng thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa nhân viên khỏi chuồng: " + e.getMessage());
        }
    }

    /**
     * Xử lý logic caretakers
     * - Chỉ còn lại trường caretakers (nhiều người)
     * - Nếu nhận vào danh sách employee chỉ có id, sẽ lấy đủ thông tin từ repository
     */
    private void handleCaretakers(PigPen pigPen) {
        if (pigPen.getCaretakers() != null) {
            Set<Employee> resolvedCaretakers = pigPen.getCaretakers().stream()
                    .map(employee -> {
                        if (employee.getEmployeeId() != null && (employee.getFullName() == null || employee.getUsername() == null)) {
                            return employeeRepository.findById(employee.getEmployeeId()).orElse(employee);
                        }
                        return employee;
                    })
                    .collect(Collectors.toSet());
            pigPen.setCaretakers(resolvedCaretakers);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePigPen(@PathVariable Long id) {
        Optional<PigPen> pigPen = pigPenService.findById(id);
        if (pigPen.isPresent()) {
            pigPenService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Search for pig pens by various criteria
     * @param name Search by pen name
     * @param caretakerName Search by caretaker name
     * @param createdDateFrom Filter by creation date (from)
     * @param createdDateTo Filter by creation date (to)
     * @param minQuantity Filter by minimum quantity
     * @param maxQuantity Filter by maximum quantity
     * @return List of pig pens matching search criteria
     */
    @GetMapping("/search")
    public List<PigPen> searchPigPens(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String caretakerName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateTo,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity) {

        return pigPenService.searchPigPens(
                name, caretakerName, createdDateFrom, createdDateTo, minQuantity, maxQuantity);
    }

    /**
     * Search for pig pens by name
     */
    @GetMapping("/search/name")
    public List<PigPen> searchPigPensByName(@RequestParam String name) {
        return pigPenService.searchByName(name);
    }

    /**
     * Search for pig pens by creation date range
     */
    @GetMapping("/search/date")
    public List<PigPen> searchPigPensByDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return pigPenService.searchByDateRange(from, to);
    }

    /**
     * Search for pig pens by quantity range
     */
    @GetMapping("/search/quantity")
    public List<PigPen> searchPigPensByQuantityRange(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {

        return pigPenService.searchByQuantityRange(min, max);
    }

    @GetMapping("/my-pens")
    public List<PigPen> getMyPigPens(@RequestParam String employeeId) {
        return pigPenService.findByEmployeeId(employeeId);
    }

    @GetMapping("/with-animal")
    public List<PigPenWithAnimalDTO> getPigPensWithAnimal() {
        List<PigPen> pens = pigPenService.findAll();
        List<PigPenWithAnimalDTO> result = new ArrayList<>();
        for (PigPen pen : pens) {
            // Lấy danh sách động vật đang nuôi trong chuồng này
            List<Animal> animals = animalRepository.findByPigPenAndRaisingStatus(pen, Animal.RaisingStatus.RAISING);
            String animalNames = animals.isEmpty() ? "Không có động vật" :
                animals.stream().map(Animal::getName).distinct().reduce((a, b) -> a + ", " + b).orElse("Không có động vật");
            result.add(new PigPenWithAnimalDTO(pen.getPenId(), pen.getName(), animalNames));
        }
        return result;
    }
}