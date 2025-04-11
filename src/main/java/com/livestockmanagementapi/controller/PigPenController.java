package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.service.pigpen.IPigPenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pigpens")
@CrossOrigin("*")
public class PigPenController {

    @Autowired
    private IPigPenService pigPenService;

    @GetMapping
    public List<PigPen> getAll() {
        return pigPenService.findAll();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PigPen> getById(@PathVariable Long id) {
//        return pigPenService.getById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public PigPen create(@RequestBody PigPen pigPen) {
//        return pigPenService.create(pigPen);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<PigPen> update(@PathVariable Long id, @RequestBody PigPen pigPen) {
//        PigPen updated = pigPenService.update(id, pigPen);
//        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        pigPenService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}

