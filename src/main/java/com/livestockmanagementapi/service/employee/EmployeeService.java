package com.livestockmanagementapi.service.employee;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    @Autowired
    private final EmployeeRepository employeeRepository;


    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {

    }

    public String generateEmployeeId() {
        long count = employeeRepository.count(); // Đếm số lượng hiện tại
        return String.format("EMP%03d", count + 1); // Ví dụ: EMP001
    }


}
