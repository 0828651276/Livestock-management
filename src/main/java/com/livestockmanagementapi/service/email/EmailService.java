package com.livestockmanagementapi.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendAccountInfoEmail(String to, String username, String password, String fullName) throws MessagingException {
        String subject = "Thông tin tài khoản truy cập hệ thống";

        String loginUrl = "http://localhost:5173/login";

        String htmlContent = "<p>Chào <b>" + fullName + "</b>,</p>"
                + "<p>Tài khoản truy cập của bạn như sau:</p>"
                + "<ul>"
                + "<li><b>\uD83D\uDCE7 Tài khoản:</b> " + username + "</li>"
                + "<li><b>\uD83D\uDD10 Mật khẩu:</b> " + password + "</li>"
                + "</ul>"
                + "<p>\uD83D\uDC49 Bạn có thể đăng nhập tại: <a href='" + loginUrl + "'>" + loginUrl + "</a></p>"
                + "<p>Vui lòng đổi mật khẩu sau khi đăng nhập.</p>"
                + "<br><p>Trân trọng,<br>Ban quản trị hệ thống.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true để gửi HTML
        helper.setFrom("nguyenxuanloc.02042001s@gmail.com");

        mailSender.send(message);
    }

    public void sendPasswordChangedNotification(String to) throws MessagingException {
        String subject = "Mật khẩu đã được thay đổi";

        String html = "<p>Xin chào,</p>"
                + "<p>Mật khẩu tài khoản của bạn đã được thay đổi thành công.</p>"
                + "<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ quản trị viên ngay lập tức.</p>"
                + "<br><p>Trân trọng,<br>Hệ thống quản lý</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        helper.setFrom("nguyenxuanloc.02042001s@gmail.com");

        mailSender.send(message);
    }

}

