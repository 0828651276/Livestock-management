package com.livestockmanagementapi.service.employee;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.IGenericService;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService extends IGenericService<Employee> {
    Optional<Employee> findByIdString(String id);

    void deleteByIdString(String id);

    List<Employee> search(String id, String name);
}