package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Vaccination;
import com.livestockmanagementapi.service.vaccination.VaccinationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccinations")
@CrossOrigin(origins = "*")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @GetMapping
    public ResponseEntity<List<Vaccination>> getAllVaccinations() {
        return ResponseEntity.ok(vaccinationService.findAll());
    }

    /**
     * Get records by pigId
     */
    @GetMapping("/animal/{pigId}")
    public ResponseEntity<List<Vaccination>> getByAnimal(@PathVariable Long pigId) {
        try {
            List<Vaccination> list = vaccinationService.findByAnimalId(pigId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get record by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vaccination> getById(@PathVariable Long id) {
        Optional<Vaccination> vac = vaccinationService.findById(id);
        return vac.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create new vaccination record
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Vaccination vaccination) {
        try {
            if (vaccination.getStatus() == null) {
                vaccination.setStatus(Vaccination.Status.SCHEDULED);
            }
            vaccinationService.save(vaccination);
            return ResponseEntity.status(HttpStatus.CREATED).body(vaccination);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Update existing vaccination record
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody Vaccination vaccination) {
        Optional<Vaccination> existing = vaccinationService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vaccination v = existing.get();
        v.setAnimal(vaccination.getAnimal());
        v.setDate(vaccination.getDate());
        v.setVaccine(vaccination.getVaccine());
        v.setNote(vaccination.getNote());
        v.setStatus(vaccination.getStatus());
        vaccinationService.save(v);
        return ResponseEntity.ok(v);
    }

    /**
     * Delete a vaccination record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Vaccination> existing = vaccinationService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vaccinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get vaccination records by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vaccination>> getByStatus(@PathVariable Vaccination.Status status) {
        return ResponseEntity.ok(vaccinationService.findByStatus(status));
    }

    /**
     * Get vaccination records by animal and status
     */
    @GetMapping("/animal/{pigId}/status/{status}")
    public ResponseEntity<List<Vaccination>> getByAnimalAndStatus(@PathVariable Long pigId, @PathVariable Vaccination.Status status) {
        return ResponseEntity.ok(vaccinationService.findByAnimalIdAndStatus(pigId, status));
    }
}
