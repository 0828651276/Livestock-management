package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.employee.EmployeeService;
import com.livestockmanagementapi.service.uploadFile.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final StorageService storageService;

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
    @PostMapping("/")
    public ResponseEntity<Employee> saveEmployeeWithImage(
            @RequestPart("employee") Employee employee,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) {
        // Mật khẩu mặc định
        String defaultPassword = "123456";
        employee.setPassword(defaultPassword);

        if (avatar != null && !avatar.isEmpty()) {
            String fileName = storageService.storeWithUUID(avatar);
            employee.setImagePath(fileName);
        }
        employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // Cập nhật nhân viên
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable String employeeId,
            @RequestParam String fullName,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam LocalDate birthDate,
            @RequestParam String gender,
            @RequestParam String idCardNumber,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) {
        Optional<Employee> existing = employeeService.findByIdString(employeeId);

        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhân viên không tồn tại");
        }

        Employee employee = existing.get();
        employee.setFullName(fullName);
        employee.setUsername(username);
        employee.setEmail(email);
        employee.setBirthDate(birthDate);
        employee.setGender(gender);
        employee.setIdCardNumber(idCardNumber);

        if (avatar != null && !avatar.isEmpty()) {
            String fileName = storageService.storeWithUUID(avatar);
            employee.setImagePath(fileName);
        }

        employeeService.save(employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        Optional<Employee> employee = employeeService.findByIdString(id);
        if (employee.isPresent()) {
            // Xoá file ảnh nếu tồn tại
            String avatarPath = employee.get().getImagePath(); // đường dẫn đến file ảnh
            if (avatarPath != null) {
                File avatarFile = new File(avatarPath);
                if (avatarFile.exists()) {
                    avatarFile.delete();
                }
            }

            // Xoá nhân viên khỏi CSDL
            employeeService.deleteByIdString(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
