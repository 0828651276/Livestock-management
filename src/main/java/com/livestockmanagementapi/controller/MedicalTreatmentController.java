package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.MedicalTreatment;
import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.dto.animal.AnimalSimpleDTO;
import com.livestockmanagementapi.model.dto.medical.MedicalTreatmentDTO;
import com.livestockmanagementapi.model.dto.medical.MedicalTreatmentUpdateDTO;
import com.livestockmanagementapi.service.medical.MedicalTreatmentService;
import com.livestockmanagementapi.repository.AnimalRepository;
import com.livestockmanagementapi.repository.MedicalTreatmentRepository;
import com.livestockmanagementapi.repository.PigPenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/medical-treatments")
@CrossOrigin("*")
public class MedicalTreatmentController {
    @Autowired
    private MedicalTreatmentService service;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private MedicalTreatmentRepository medicalTreatmentRepository;
    @Autowired
    private PigPenRepository pigPenRepository;

    @PutMapping("/{id}")
    public ResponseEntity<MedicalTreatmentDTO> update(@PathVariable Long id, @RequestBody MedicalTreatmentUpdateDTO dto) {
        Optional<MedicalTreatment> existing = medicalTreatmentRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MedicalTreatment treatment = existing.get();
        treatment.setAnimal(animalRepository.findById(dto.getPigId()).orElse(null));
        treatment.setPen(pigPenRepository.findById(dto.getPenId()).orElse(null));
        treatment.setDate(dto.getDate());
        treatment.setTreatmentType(dto.getTreatmentType());
        treatment.setVeterinarian(dto.getVeterinarian());
        treatment.setMedicine(dto.getMedicine());
        treatment.setNote(dto.getNote());
        MedicalTreatment saved = medicalTreatmentRepository.save(treatment);
        return ResponseEntity.ok(new MedicalTreatmentDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!medicalTreatmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicalTreatmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<MedicalTreatment>> search(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.search(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalTreatment> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // API lấy danh sách animal có status SICK hoặc UNVACCINATED (trả về DTO)
    @GetMapping("/animals/sick_or_unvaccinated")
    public ResponseEntity<List<AnimalSimpleDTO>> getSickOrUnvaccinatedAnimals() {
        try {
            List<Animal> animals = animalRepository.findByStatusIn(List.of("SICK", "UNVACCINATED"));
            List<AnimalSimpleDTO> dtos = new ArrayList<>();
            for (Animal animal : animals) {
                dtos.add(new AnimalSimpleDTO(animal));
                // Tạo mới MedicalTreatment cho animal nếu chưa có trong ngày
                MedicalTreatment mt = new MedicalTreatment();
                mt.setAnimal(animal);
                mt.setDate(LocalDate.now());
                mt.setTreatmentType("");
                mt.setVeterinarian("");
                mt.setMedicine("");
                mt.setNote("");
                mt.setPen(animal.getPigPen()); // Nếu Animal có quan hệ với Pen
                medicalTreatmentRepository.save(mt);
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }
}
