package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.repository.EmployeeRepository;
import com.livestockmanagementapi.service.pigpen.IPigPenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
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
        // Xử lý cả caretaker và caretakers
        handleCaretakers(pigPen);
        pigPenService.save(pigPen);
        return ResponseEntity.ok(pigPen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PigPen> updatePigPen(@PathVariable Long id, @RequestBody PigPen updatedPigPen) {
        Optional<PigPen> existing = pigPenService.findById(id);
        if (existing.isPresent()) {
            updatedPigPen.setPenId(id);
            // Xử lý cả caretaker và caretakers
            handleCaretakers(updatedPigPen);
            pigPenService.save(updatedPigPen);
            return ResponseEntity.ok(updatedPigPen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Xử lý logic caretakers
     * - Nếu có caretaker đơn lẻ và không có caretakers, thêm caretaker vào caretakers
     * - Nếu có caretakers nhưng không có caretaker, đặt caretaker là người đầu tiên trong caretakers
     */
    private void handleCaretakers(PigPen pigPen) {
        // Nếu có caretaker đơn lẻ nhưng không có trong caretakers, thêm vào
        if (pigPen.getCaretaker() != null) {
            if (pigPen.getCaretakers() == null) {
                pigPen.setCaretakers(new HashSet<>());
            }
            pigPen.getCaretakers().add(pigPen.getCaretaker());
        }

        // Nếu có caretakers nhưng không có caretaker, đặt caretaker là người đầu tiên
        if (pigPen.getCaretakers() != null && !pigPen.getCaretakers().isEmpty() && pigPen.getCaretaker() == null) {
            pigPen.setCaretaker(pigPen.getCaretakers().iterator().next());
        }

        // Xử lý trường hợp nhận ID thay vì object đầy đủ
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
     * Search for pig pens by caretaker id
     */
//    @GetMapping("/search/caretaker/{caretakerId}")
//    public List<PigPen> searchPigPensByCaretaker(@PathVariable String caretakerId) {
//        return pigPenService.findByCaretakerId(caretakerId);
//    }

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

}