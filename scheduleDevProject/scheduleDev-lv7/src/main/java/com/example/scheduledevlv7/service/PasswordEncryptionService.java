package com.example.scheduledevlv7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncryptionService {

    private final PasswordEncoder passwordEncoder;  // PasswordEncoder로 수정

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);  // 비밀번호 암호화
    }

    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);  // 암호화된 비밀번호와 비교
    }
}
