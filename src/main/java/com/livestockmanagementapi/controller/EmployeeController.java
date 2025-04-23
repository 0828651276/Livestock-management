package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.repository.PigPenRepository;
import com.livestockmanagementapi.service.email.EmailService;
import com.livestockmanagementapi.service.employee.EmployeeService;
import com.livestockmanagementapi.service.uploadFile.StorageService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final StorageService storageService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PigPenRepository pigPenRepository;


    // Lấy danh sách tất cả nhân viên
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // Lấy thông tin chi tiết của nhân viên theo ID
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.findByIdString(employeeId);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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

    // Thêm nhân viên mới
    @PostMapping("/")
    public ResponseEntity<Employee> saveEmployeeWithImage(
            @RequestPart("employee") Employee employee,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) throws MessagingException {
        // Mật khẩu mặc định
        String defaultPassword = "123456";
        employee.setPassword(passwordEncoder.encode(defaultPassword));

        if (avatar != null && !avatar.isEmpty()) {
            String fileName = storageService.storeWithUUID(avatar);
            employee.setImagePath(fileName);
        }

        employeeService.save(employee);

        // Sau khi lưu thành công, gửi email thông báo
        emailService.sendAccountInfoEmail(
                employee.getEmail(),
                employee.getUsername(), // hoặc employee.getEmail()
                defaultPassword,
                employee.getFullName()
        );

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
        Optional<Employee> employeeOpt = employeeService.findByIdString(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // 1. Xử lý chuồng có nhân viên này trong danh sách caretakers
            List<PigPen> pigPensWithCaretaker = pigPenRepository.findByAnyCaretakerEmployeeId(id);
            for (PigPen pen : pigPensWithCaretaker) {
                pen.getCaretakers().remove(employee);
            }
            pigPenRepository.saveAll(pigPensWithCaretaker);

            // 2. Xoá nhân viên
            employeeService.deleteByIdString(id);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/{employeeId}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable String employeeId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        Optional<Employee> employeeOpt = employeeService.findByIdString(employeeId);

        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhân viên không tồn tại");
        }

        Employee employee = employeeOpt.get();

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu cũ không đúng");
        }

        // Cập nhật mật khẩu mới
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeService.save(employee);

        try {
            emailService.sendPasswordChangedNotification(employee.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

}
