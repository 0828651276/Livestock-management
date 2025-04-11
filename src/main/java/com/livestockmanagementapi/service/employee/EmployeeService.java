package com.livestockmanagementapi.service.employee;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    @Autowired
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;



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
        // Nếu là tạo mới hoặc password bị thay đổi → băm password
        if (employee.getPassword() != null && !employee.getPassword().startsWith("$2a$")) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Employee> findByIdString(String id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteByIdString(String id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> search(String id, String name) {
        if (id != null && name != null) {
            return employeeRepository.findByEmployeeIdContainingIgnoreCaseAndFullNameContainingIgnoreCase(id, name);
        } else if (id != null) {
            return employeeRepository.findByEmployeeIdContainingIgnoreCase(id);
        } else if (name != null) {
            return employeeRepository.findByFullNameContainingIgnoreCase(name);
        } else {
            return employeeRepository.findAll();
        }
    }


}
