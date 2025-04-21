package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.service.IGenericService;

import java.time.LocalDate;
import java.util.List;
public interface IAnimalService extends IGenericService<Animal> {

    void save(Animal animal);

    List<Animal> search(String name, String status, LocalDate entryDateFrom, LocalDate entryDateTo, Long penId);

    List<Animal> findByPenId(Long penId);

    List<Animal> findByStatus(String status);

}
