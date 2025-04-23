package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.NotificationEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationEmployeeRepository extends JpaRepository<NotificationEmployee, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM NotificationEmployee ne WHERE ne.notification.id = :notificationId")
    void deleteByNotificationId(Long notificationId);
}
