package com.livestockmanagementapi.service.notification;

import com.livestockmanagementapi.model.Notification;
import com.livestockmanagementapi.service.IGenericService;
import java.util.List;

public interface INotificationService extends IGenericService<Notification> {
    // Các phương thức bổ sung ngoài các phương thức cơ bản từ IGenericService
    List<Notification> findByContent(String content);
}