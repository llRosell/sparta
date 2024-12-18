package com.example.scheduledevlv5.service;

import com.example.scheduledevlv5.entity.Login;
import com.example.scheduledevlv5.entity.Session;
import com.example.scheduledevlv5.repository.LoginRepository;
import com.example.scheduledevlv5.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final SessionRepository sessionRepository;

    /**
     * 사용자 인증 처리
     * @param email 사용자의 이메일
     * @param password 사용자의 비밀번호
     * @return 로그인한 사용자 정보, 인증 실패시 null
     */
    public Login authenticateUser(String email, String password) {
        // 이메일로 사용자를 찾기
        Optional<Login> userOptional = loginRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            return null;  // 인증 실패
        }

        Login user = userOptional.get();

        // 비밀번호가 일치하는지 확인
        if (!user.getPassword().equals(password)) {
            return null;  // 인증 실패
        }

        // 로그인 성공 시
        return user;
    }

    /**
     * 회원가입 처리
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 성공 여부
     */
    public boolean signUp(String email, String password) {
        Optional<Login> existingUser = loginRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return false;  // 이메일이 이미 존재하는 경우
        }

        Login newUser = new Login();
        newUser.setEmail(email);
        newUser.setPassword(password);
        loginRepository.save(newUser);
        return true;  // 회원가입 성공
    }

    /**
     * 세션을 MySQL에 저장하는 메서드
     * @param sessionId 세션 ID
     * @param email 세션에 연결된 사용자 이메일
     */
    public void storeSession(String sessionId, String email) {
        Session session = Session.builder()
                .sessionId(sessionId)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
        sessionRepository.save(session);
    }

    /**
     * 세션 ID로 세션 정보 조회
     * @param sessionId 세션 ID
     * @return 세션 정보
     */
    public Optional<Session> getSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }
}
