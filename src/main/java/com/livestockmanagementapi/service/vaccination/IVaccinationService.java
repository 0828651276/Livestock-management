package com.livestockmanagementapi.service.vaccination;

import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.model.Vaccination;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDate;
import java.util.List;

public interface IVaccinationService extends IGenericService<Vaccination> {
    Vaccination saveVaccination(Vaccination vaccination);
    List<Vaccination> findByAnimalId(Long pigId);
    List<Vaccination> findByStatus(Vaccination.Status status);
    List<Vaccination> findByAnimalIdAndStatus(Long pigId, Vaccination.Status status);
}
