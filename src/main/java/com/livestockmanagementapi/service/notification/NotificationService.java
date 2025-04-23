package com.livestockmanagementapi.service.notification;

import com.livestockmanagementapi.model.Notification;
import com.livestockmanagementapi.model.NotificationEmployee;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.repository.NotificationRepository;
import com.livestockmanagementapi.repository.NotificationEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationEmployeeRepository notificationEmployeeRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAllByOrderByPostedAtDescIdDesc();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void save(Notification notification) {
        if (notification.getPostedAt() == null) {
            notification.setPostedAt(LocalDateTime.now());
        }
        notificationRepository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> findByContent(String content) {
        return notificationRepository.findByContentContainingIgnoreCase(content);
    }

    public void notifyEmployees(Notification notification, Set<Employee> employees) {
        for (Employee employee : employees) {
            NotificationEmployee ne = new NotificationEmployee();
            ne.setNotification(notification);
            ne.setEmployee(employee);
            ne.setRead(false);
            notificationEmployeeRepository.save(ne);
        }
    }

    @Override
    public void deleteNotificationEmployeesByNotificationId(Long notificationId) {
        notificationEmployeeRepository.deleteByNotificationId(notificationId);
    }
}