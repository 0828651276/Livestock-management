package com.livestockmanagementapi.service.pigpen;

import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.repository.PigPenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}