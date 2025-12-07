package com.precious.watchapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for Precious Time Order");
        message.setText("Your OTP for confirming your order is: " + otp);
        mailSender.send(message);
    }

    public void sendConfirmationEmail(String to, String name, String deliveryDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Precious Time Order is Confirmed");
        message.setText("Hello " + name + ",\n\n" +
                "Your order has been successfully confirmed!\n" +
                "Estimated delivery: " + deliveryDate + "\n\n" +
                "Thank you for choosing Precious Time!");
        mailSender.send(message);
    }
}
