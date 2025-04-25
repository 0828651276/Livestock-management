package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.Vaccination;
import com.livestockmanagementapi.repository.PigPenRepository;
import com.livestockmanagementapi.service.vaccination.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccinations")
@CrossOrigin(origins = "*")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private PigPenRepository pigPenRepository;

    @GetMapping
    public List<Vaccination> getAll() {
        return vaccinationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Vaccination> getById(@PathVariable Long id) {
        return vaccinationService.findById(id);
    }

    @PostMapping
    public Vaccination create(@RequestBody Vaccination vaccination) {
        Optional<PigPen> pen = pigPenRepository.findById(vaccination.getPen().getPenId());
        pen.ifPresent(vaccination::setPen);
        return vaccinationService.saveVaccination(vaccination);
    }

    @PutMapping("/{id}")
    public Vaccination update(@PathVariable Long id, @RequestBody Vaccination updatedVaccination) {
        Optional<Vaccination> existing = vaccinationService.findById(id);
        if (existing.isPresent()) {
            Vaccination v = existing.get();
            v.setDate(updatedVaccination.getDate());
            v.setVaccineType(updatedVaccination.getVaccineType());
            v.setNote(updatedVaccination.getNote());
            Optional<PigPen> pen = pigPenRepository.findById(updatedVaccination.getPen().getPenId());
            pen.ifPresent(v::setPen);
            return vaccinationService.saveVaccination(v);
        } else {
            throw new RuntimeException("Không tìm thấy tiêm phòng với ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vaccinationService.deleteById(id);
    }
}
