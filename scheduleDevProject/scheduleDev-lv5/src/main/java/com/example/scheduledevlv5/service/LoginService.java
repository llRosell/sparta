package com.example.scheduledevlv5.service;

import com.example.scheduledevlv5.entity.Login;
import com.example.scheduledevlv5.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    // 회원가입 시 사용자 추가 (암호화 없이 평문 비밀번호 사용)
    public void signUp(String email, String password) {
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);  // 암호화 없이 저장

        loginRepository.save(login);
    }

    // 로그인 인증 처리 (평문 비밀번호 비교)
    public Login authenticateUser(String email, String password) {
        Optional<Login> optionalUser = loginRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        Login login = optionalUser.get();

        // 평문 비밀번호를 직접 비교
        if (!password.equals(login.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return login;  // 인증 성공 시 로그인 객체 반환
    }
}
