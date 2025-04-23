package com.livestockmanagementapi.repository;

import com.livestockmanagementapi.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Tìm thông báo theo thời gian đăng
    List<Notification> findByPostedAt(LocalDateTime postedAt);

    // Tìm thông báo theo nội dung (tìm kiếm gần đúng, không phân biệt hoa thường)
    List<Notification> findByContentContainingIgnoreCase(String content);

    // Tìm theo thời gian đăng, sắp xếp giảm dần theo ID
    List<Notification> findByPostedAtOrderByIdDesc(LocalDateTime postedAt);

    // Tìm tất cả, sắp xếp giảm dần theo thời gian đăng và ID
    List<Notification> findAllByOrderByPostedAtDescIdDesc();
}
