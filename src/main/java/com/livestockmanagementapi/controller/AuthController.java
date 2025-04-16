package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.dto.AuthRequest;
import com.livestockmanagementapi.model.dto.AuthResponse;
import com.livestockmanagementapi.repository.EmployeeRepository;
import com.livestockmanagementapi.security.JwtUtil;
import com.livestockmanagementapi.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authenticationManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());
            authenticationManager.authenticate(authToken);

            String token = jwtUtil.generateToken(request.getUsername());

            // ✅ Tìm employeeId từ username
            String employeeId = employeeRepository
                    .findByUsername(request.getUsername())
                    .map(emp -> emp.getEmployeeId())
                    .orElse(null);

            return new AuthResponse(token, employeeId);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
