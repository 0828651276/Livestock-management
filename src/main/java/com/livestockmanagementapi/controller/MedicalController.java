package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.service.medical.IMedicalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medical")
@CrossOrigin("*")
@Validated
public class MedicalController {

    @Autowired
    private IMedicalService medicalService;

    @GetMapping
    public ResponseEntity<List<Medical>> getAllMedical() {
        return ResponseEntity.ok(medicalService.findAll());
    }

    /**
     * Get records by pigId
     */
    @GetMapping("/animal/{pigId}")
    public ResponseEntity<List<Medical>> getByAnimal(@PathVariable Long pigId) {
        try {
            List<Medical> list = medicalService.findByAnimalId(pigId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get record by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medical> getById(@PathVariable Long id) {
        Optional<Medical> med = medicalService.findById(id);
        return med.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create new medical record
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Medical medical) {
        try {
            if (medical.getStatus() == null) {
                medical.setStatus(Medical.Status.SCHEDULED);
            }
            medicalService.save(medical);
            return ResponseEntity.status(HttpStatus.CREATED).body(medical);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Update existing medical record
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody Medical medical) {
        Optional<Medical> existing = medicalService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Medical m = existing.get();
        m.setAnimal(medical.getAnimal());
        m.setTreatmentDate(medical.getTreatmentDate());
        m.setTreatmentMethod(medical.getTreatmentMethod());
        m.setVeterinarian(medical.getVeterinarian());
        m.setNotes(medical.getNotes());
        m.setStatus(medical.getStatus());
        medicalService.save(m);
        return ResponseEntity.ok(m);
    }

    /**
     * Delete a medical record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Medical> existing = medicalService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        medicalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get medical records by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Medical>> getByStatus(@PathVariable Medical.Status status) {
        return ResponseEntity.ok(medicalService.findByStatus(status));
    }

    /**
     * Get medical records by animal and status
     */
    @GetMapping("/animal/{pigId}/status/{status}")
    public ResponseEntity<List<Medical>> getByAnimalAndStatus(@PathVariable Long pigId, @PathVariable Medical.Status status) {
        return ResponseEntity.ok(medicalService.findByAnimalIdAndStatus(pigId, status));
    }
} 