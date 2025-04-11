package com.livestockmanagementapi.service.animal;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.service.IGenericService;

import java.util.List;
import java.util.Optional;

public interface IAnimalService extends IGenericService<Animal> {

    void save(Animal animal);
}
