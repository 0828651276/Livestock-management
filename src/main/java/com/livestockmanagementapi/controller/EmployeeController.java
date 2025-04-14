package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Lấy danh sách tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // Tìm nhân viên theo ID hoặc Tên
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> search(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name
    ) {
        List<Employee> result = employeeService.search(id, name);
        return ResponseEntity.ok(result);
    }

    // Thêm hoặc cập nhật nhân viên
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    // Cập nhật nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> existing = employeeService.findByIdString(id);

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = existing.get();
        employee.setFullName(updatedEmployee.getFullName());
        employee.setUsername(updatedEmployee.getUsername());
        employee.setPassword(updatedEmployee.getPassword());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setBirthDate(updatedEmployee.getBirthDate());
        employee.setGender(updatedEmployee.getGender());
        employee.setIdCardNumber(updatedEmployee.getIdCardNumber());
        employee.setRole(updatedEmployee.getRole());

        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    // Xoá nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        Optional<Employee> employee = employeeService.findByIdString(id);
        if (employee.isPresent()) {
            employeeService.deleteByIdString(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
