package com.livestockmanagementapi.service.notification;

import com.livestockmanagementapi.model.Notification;
import com.livestockmanagementapi.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

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
}