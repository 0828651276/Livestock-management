package com.livestockmanagementapi.service.pigpen;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDate;
import java.util.List;


public interface IPigPenService extends IGenericService<PigPen> {
    void save(PigPen pigPen);

    List<PigPen> searchPigPens(String name, String caretakerName, LocalDate createdDateFrom, LocalDate createdDateTo, Integer minQuantity, Integer maxQuantity);

    List<PigPen> searchByName(String name);

    List<PigPen> searchByDateRange(LocalDate from, LocalDate to);

    List<PigPen> findByCaretakerId(Long caretakerId);

    List<PigPen> searchByQuantityRange(Integer min, Integer max);
}

