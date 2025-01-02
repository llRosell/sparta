package com.example.scheduledevtesterlv1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncryptionService {

    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 PasswordEncoder 의존성 주입

    /**
     * 비밀번호를 암호화하는 메서드
     * @param password 원본 비밀번호
     * @return 암호화된 비밀번호
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);  // PasswordEncoder를 사용하여 비밀번호를 암호화하여 반환
    }

    /**
     * 입력한 비밀번호가 암호화된 비밀번호와 일치하는지 비교하는 메서드
     * @param rawPassword 입력된 원본 비밀번호
     * @param encryptedPassword 저장된 암호화된 비밀번호
     * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
     */
    public boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);  // 원본 비밀번호와 암호화된 비밀번호를 비교하여 일치 여부 반환
    }
}
