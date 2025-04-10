package com.livestockmanagementapi.service.user;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.IGenericService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserService extends IGenericService {
    UserDetails loadUserByUsername(String username);
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String email);
    void update(Employee employee);
    void changePassword(Long userId, String newPassword);
}
