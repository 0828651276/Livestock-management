package com.livestockmanagementapi.service.vaccination;

import com.livestockmanagementapi.model.Vaccination;
import com.livestockmanagementapi.service.IGenericService;

public interface IVaccinationService extends IGenericService<Vaccination> {
    Vaccination saveVaccination(Vaccination vaccination);
}
