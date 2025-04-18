package com.livestockmanagementapi.service.pigpen;

import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.repository.PigPenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PigPenService implements IPigPenService {

    @Autowired
    private PigPenRepository pigPenRepository;

    @Override
    public List<PigPen> findAll() {
        return pigPenRepository.findAll();
    }

    @Override
    public Optional<PigPen> findById(Long id) {
        return pigPenRepository.findById(id);
    }

    @Override
    public void save(PigPen pigPen) {
        pigPenRepository.save(pigPen);
    }

    @Override
    public void deleteById(Long id) {
        pigPenRepository.deleteById(id);
    }

    @Override
    public List<PigPen> searchPigPens(
            String name,
            String caretakerName,
            LocalDate createdDateFrom,
            LocalDate createdDateTo,
            Integer minQuantity,
            Integer maxQuantity) {

        List<PigPen> allPigPens = findAll();

        // Apply filters based on provided parameters
        return allPigPens.stream()
                .filter(pen -> name == null ||
                        (pen.getName() != null && pen.getName().toLowerCase().contains(name.toLowerCase())))
                // Tìm kiếm trong tất cả caretakers
                .filter(pen -> caretakerName == null ||
                        (pen.getCaretakers() != null && pen.getCaretakers().stream()
                                .anyMatch(caretaker -> caretaker.getFullName() != null &&
                                        caretaker.getFullName().toLowerCase().contains(caretakerName.toLowerCase()))))
                .filter(pen -> createdDateFrom == null ||
                        (pen.getCreatedDate() != null &&
                                !pen.getCreatedDate().isBefore(createdDateFrom)))
                .filter(pen -> createdDateTo == null ||
                        (pen.getCreatedDate() != null &&
                                !pen.getCreatedDate().isAfter(createdDateTo)))
                .filter(pen -> minQuantity == null || pen.getQuantity() >= minQuantity)
                .filter(pen -> maxQuantity == null || pen.getQuantity() <= maxQuantity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PigPen> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }

        return pigPenRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Override
    public List<PigPen> searchByDateRange(LocalDate from, LocalDate to) {
        List<PigPen> allPens = findAll();

        if (from == null && to == null) {
            return allPens;
        }

        return allPens.stream()
                .filter(pen -> pen.getCreatedDate() != null)
                .filter(pen -> {
                    boolean matchesFromDate = from == null ||
                            !pen.getCreatedDate().isBefore(from);
                    boolean matchesToDate = to == null ||
                            !pen.getCreatedDate().isAfter(to);
                    return matchesFromDate && matchesToDate;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PigPen> findByCaretakerId(String caretakerId) {
        if (caretakerId == null) {
            return List.of();
        }

        // Tìm trong cả caretaker và caretakers
        List<PigPen> byCaretaker = pigPenRepository.findByCaretakerEmployeeId(caretakerId);
        List<PigPen> byAnyCaretaker = pigPenRepository.findByAnyCaretakerEmployeeId(caretakerId);

        // Kết hợp kết quả và loại bỏ trùng lặp
        return byCaretaker.stream()
                .filter(pen -> !byAnyCaretaker.contains(pen))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<PigPen> searchByQuantityRange(Integer min, Integer max) {
        return findAll().stream()
                .filter(pen -> min == null || pen.getQuantity() >= min)
                .filter(pen -> max == null || pen.getQuantity() <= max)
                .collect(Collectors.toList());
    }
}