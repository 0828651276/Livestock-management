package com.livestockmanagementapi.service.employee;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.IGenericService;

public interface IEmployeeService extends IGenericService<Employee> {
    String generateEmployeeId();

}
