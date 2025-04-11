package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.service.pigpen.IPigPenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pigpens")
@CrossOrigin("*")
public class PigPenController {

    @Autowired
    private IPigPenService pigPenService;

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
        pigPenService.save(pigPen);
        return ResponseEntity.ok(pigPen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PigPen> updatePigPen(@PathVariable Long id, @RequestBody PigPen updatedPigPen) {
        Optional<PigPen> existing = pigPenService.findById(id);
        if (existing.isPresent()) {
            updatedPigPen.setPenId(id);
            pigPenService.save(updatedPigPen);
            return ResponseEntity.ok(updatedPigPen);
        } else {
            return ResponseEntity.notFound().build();
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
}
