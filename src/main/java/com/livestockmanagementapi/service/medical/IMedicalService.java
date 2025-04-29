package com.livestockmanagementapi.service.medical;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.service.IGenericService;
import java.time.LocalDate;
import java.util.List;

public interface IMedicalService extends IGenericService<Medical> {
    /**
     * Find medical records associated with given animal pigId
     */
    List<Medical> findByAnimalId(Long pigId);

    List<Medical> findByTreatmentDateLessThanEqual(LocalDate now);

    /**
     * Find medical records before a specific date
     */

    List<Medical> findByStatus(Medical.Status status);
    List<Medical> findByAnimalIdAndStatus(Long pigId, Medical.Status status);
} 