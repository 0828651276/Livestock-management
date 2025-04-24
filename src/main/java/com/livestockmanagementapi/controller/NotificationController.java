package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.model.Notification;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.service.notification.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping
    public List<Notification> findAll() {
        return notificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Notification> searchNotifications(@RequestParam String content) {
        return notificationService.findByContent(content);
    }

    @PostMapping
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        notification.setPostedAt(LocalDateTime.now());
        notificationService.save(notification);

        // Lấy danh sách employee chăm sóc các chuồng liên quan
        Set<Employee> allEmployees = new HashSet<>();
        for (PigPen pigPen : notification.getPigPens()) {
            allEmployees.addAll(pigPen.getCaretakers());
        }
        notificationService.notifyEmployees(notification, allEmployees);

        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification updatedNotification) {
        Optional<Notification> existing = notificationService.findById(id);
        if (existing.isPresent()) {
            updatedNotification.setId(id);
            updatedNotification.setPostedAt(LocalDateTime.now());
            notificationService.save(updatedNotification);

            // Xoá các bản ghi notification_employee cũ
            notificationService.deleteNotificationEmployeesByNotificationId(id);

            // Lấy lại danh sách employee từ các PigPen mới
            Set<Employee> allEmployees = new HashSet<>();
            for (PigPen pigPen : updatedNotification.getPigPens()) {
                allEmployees.addAll(pigPen.getCaretakers());
            }
            notificationService.notifyEmployees(updatedNotification, allEmployees);

            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        if (notification.isPresent()) {
            notificationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Optional<Notification> notificationOpt = notificationService.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.setRead(true);
            notificationService.save(notification);
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }
}