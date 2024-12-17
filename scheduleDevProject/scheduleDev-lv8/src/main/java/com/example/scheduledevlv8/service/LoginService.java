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

    private final LoginRepository loginRepository;  // 로그인 관련 데이터를 처리하는 리포지토리
    private final PasswordEncryptionService passwordEncryptionService;  // 비밀번호 암호화 서비스

    /**
     * 사용자 회원가입 처리
     * @param email 사용자의 이메일
     * @param password 사용자의 비밀번호
     * @return 회원가입 성공 여부
     */
    public boolean signUp(String email, String password) {
        // 이메일 중복 체크: 이미 존재하는 이메일인지 확인
        Optional<Login> existingUser = loginRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.error("회원가입 실패: 이미 존재하는 이메일입니다. email={}", email);  // 이미 존재하는 이메일에 대해 로그 기록
            return false;  // 이미 존재하는 이메일인 경우, 회원가입 실패 처리
        }

        // 비밀번호 암호화: 비밀번호를 안전하게 암호화하여 저장
        String encryptedPassword = passwordEncryptionService.encodePassword(password);

        // 새로운 사용자 정보 저장
        Login newUser = new Login(email, encryptedPassword);  // 새로운 로그인 객체 생성
        loginRepository.save(newUser);  // DB에 사용자 정보 저장
        return true;  // 회원가입 성공
    }

    /**
     * 사용자 인증 처리
     * @param email 사용자의 이메일
     * @param password 사용자의 비밀번호
     * @return 인증된 사용자 정보, 인증 실패시 null
     */
    public Login authenticateUser(String email, String password) {
        // 주어진 이메일로 사용자를 찾기
        Optional<Login> userOptional = loginRepository.findByEmail(email);

        // 사용자가 존재하지 않으면 null 반환
        if (!userOptional.isPresent()) {
            return null;  // 사용자가 존재하지 않으면 null 반환
        }

        Login user = userOptional.get();  // Optional에서 사용자 객체 추출

        // 비밀번호가 일치하는지 확인
        if (!passwordEncryptionService.matches(password, user.getPassword())) {
            return null;  // 비밀번호가 일치하지 않으면 null 반환
        }

        return user;  // 비밀번호가 일치하면 인증된 사용자 객체 반환
    }
}