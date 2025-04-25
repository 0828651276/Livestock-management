package com.livestockmanagementapi.service.notification;

import com.livestockmanagementapi.model.Notification;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.service.IGenericService;
import java.util.List;
import java.util.Set;

public interface INotificationService extends IGenericService<Notification> {
    // Các phương thức bổ sung ngoài các phương thức cơ bản từ IGenericService
    List<Notification> findByContent(String content);
    void notifyEmployees(Notification notification, Set<Employee> employees);
    void deleteNotificationEmployeesByNotificationId(Long notificationId);
}