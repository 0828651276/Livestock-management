package com.livestockmanagementapi.service.medicalremind;

import com.livestockmanagementapi.model.Animal;
import com.livestockmanagementapi.model.Medical;
import com.livestockmanagementapi.model.PigPen;
import com.livestockmanagementapi.model.Employee;
import com.livestockmanagementapi.repository.MedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class MedicalReminderService {

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 8 * * *") // chạy mỗi ngày lúc 8h sáng
    public void sendMedicalReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Medical> medicals = medicalRepository.findByTreatmentDate(tomorrow);

        for (Medical medical : medicals) {
            Animal animal = medical.getAnimal();
            if (animal == null) continue;
            PigPen pigPen = animal.getPigPen();
            if (pigPen == null) continue;
            Set<Employee> caretakers = pigPen.getCaretakers();
            if (caretakers != null && !caretakers.isEmpty()) {
                for (Employee employee : caretakers) {
                    if (employee != null && employee.getEmail() != null && !employee.getEmail().isEmpty()) {
                        sendEmail(employee.getEmail(), animal, medical);
                    }
                }
            }
        }
    }

    // Gửi email nhắc lịch khám cho các lịch khám diễn ra NGAY HÔM NAY (dùng để test)
    public void sendMedicalRemindersToday() {
        LocalDate today = LocalDate.now();
        List<Medical> medicals = medicalRepository.findByTreatmentDate(today);

        for (Medical medical : medicals) {
            Animal animal = medical.getAnimal();
            if (animal == null) continue;
            PigPen pigPen = animal.getPigPen();
            if (pigPen == null) continue;
            Set<Employee> caretakers = pigPen.getCaretakers();
            if (caretakers != null && !caretakers.isEmpty()) {
                for (Employee employee : caretakers) {
                    if (employee != null && employee.getEmail() != null && !employee.getEmail().isEmpty()) {
                        sendEmail(employee.getEmail(), animal, medical);
                    }
                }
            }
        }
    }

    private void sendEmail(String to, Animal animal, Medical medical) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Nhắc lịch khám cho " + animal.getName());
        message.setText("Bạn có lịch khám cho " + animal.getName() +
                        " vào ngày " + medical.getTreatmentDate() +
                        ". Ghi chú: " + (medical.getNotes() != null ? medical.getNotes() : "Không có ghi chú."));
        mailSender.send(message);
    }
}
