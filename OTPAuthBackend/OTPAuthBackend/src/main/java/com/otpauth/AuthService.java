package com.otpauth;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final OtpRepository otpRepo;

    public String register(String phone) {
        User user = userRepo.findByPhone(phone)
                .orElse(new User(null, phone, false));
        userRepo.save(user);
        return "User registered. Request OTP.";
    }

    public String generateOtp(String phone) {
        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000);
        Otp otp = new Otp(null, phone, otpCode, LocalDateTime.now().plusMinutes(5));
        otpRepo.save(otp);
        System.out.println("Generated OTP: " + otpCode);
        return "OTP generated (check console).";
    }

    public String verifyOtp(String phone, String code) {
        Otp otp = otpRepo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiryTime().isBefore(LocalDateTime.now()))
            return "OTP expired";

        if (!otp.getCode().equals(code))
            return "Invalid OTP";

        User user = userRepo.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setVerified(true);
        userRepo.save(user);

        otpRepo.delete(otp);
        return "User verified successfully";
    }
}
