package com.bank.allservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to generate OTP
    public String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(999999); // Generate a 6-digit OTP
        return String.format("%06d", otp);  // Format OTP to be always 6 digits
    }

    // Method to send OTP to the provided email
    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your One-Time Password (OTP)");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }
}

