package com.example.scheduledevlv8.service;

import com.example.scheduledevlv8.entity.Login;
import com.example.scheduledevlv8.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncryptionService passwordEncryptionService;

    public boolean signUp(String email, String password) {
        // 이메일 중복 체크
        Optional<Login> existingUser = loginRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.error("회원가입 실패: 이미 존재하는 이메일입니다. email={}", email);
            return false;  // 이미 존재하는 이메일인 경우
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncryptionService.encodePassword(password);

        // 새로운 사용자 정보 저장
        Login newUser = new Login(email, encryptedPassword);
        loginRepository.save(newUser);
        return true;  // 회원가입 성공
    }

    public Login authenticateUser(String email, String password) {
        // Optional에서 Login 객체 추출
        Optional<Login> userOptional = loginRepository.findByEmail(email);

        // 값이 존재하지 않으면 null 반환
        if (!userOptional.isPresent()) {
            return null;
        }

        Login user = userOptional.get(); // Optional에서 값 추출

        // 비밀번호가 일치하는지 확인
        if (!passwordEncryptionService.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }
}