package com.livestockmanagementapi.controller;

import com.livestockmanagementapi.service.medicalremind.MedicalReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-reminder")
public class MedicalReminderController {

    @Autowired
    private MedicalReminderService medicalReminderService;

    @PostMapping("/test-today")
    public String sendRemindersToday() {
        medicalReminderService.sendMedicalRemindersToday();
        return "Đã gửi email nhắc lịch khám hôm nay (nếu có dữ liệu)!";
    }
}
