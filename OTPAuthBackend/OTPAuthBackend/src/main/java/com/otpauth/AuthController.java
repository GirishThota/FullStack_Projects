package com.otpauth;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        return service.register(body.get("phone"));
    }

    @PostMapping("/request-otp")
    public String requestOtp(@RequestBody Map<String, String> body) {
        return service.generateOtp(body.get("phone"));
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody Map<String, String> body) {
        return service.verifyOtp(body.get("phone"), body.get("code"));
    }
}
