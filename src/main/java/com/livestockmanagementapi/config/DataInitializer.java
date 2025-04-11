package com.livestockmanagementapi.config;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository repository;
    private final PasswordEncoder encoder;

    public DataInitializer(EmployeeRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repository.findByUsername("admin").isEmpty()) {
            Employee admin = new Employee();
            admin.setEmployeeId("ADIMIN001");
            admin.setFullName("System Admin");
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123456"));
            admin.setRole(Employee.Role.MANAGER);
            repository.save(admin);

            System.out.println("✅ Default admin created: username=admin / password=123456");
        }
    }
}
