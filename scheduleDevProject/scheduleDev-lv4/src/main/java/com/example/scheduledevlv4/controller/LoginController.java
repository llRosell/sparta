package com.example.scheduledevlv4.controller;

import com.example.scheduledevlv4.dto.SignUpRequestDto;
import com.example.scheduledevlv4.dto.SignUpResponseDto;
import com.example.scheduledevlv4.entity.Login;
import com.example.scheduledevlv4.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;  // Add this

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        try {
            // 회원가입 처리
            loginService.signUp(requestDto.getEmail(), requestDto.getPassword());  // Correct the usage of loginService
            return new ResponseEntity<>(
                    new SignUpResponseDto(requestDto.getEmail(), "Signup successful"),
                    HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            // 예외 처리 및 HTTP 상태 코드 반환
            return ResponseEntity.status(e.getStatusCode()).body(new SignUpResponseDto(e.getReason()));
        }
    }

    // 세션 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignUpRequestDto requestDto, HttpServletRequest request) {
        try {
            // 이메일과 비밀번호로 사용자 인증
            Login login = loginService.authenticateUser(requestDto.getEmail(), requestDto.getPassword());

            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession(true);  // 세션이 없으면 새로 생성
            session.setAttribute("email", login.getEmail());

            // 세션에 저장된 사용자 ID 로그로 확인
            log.info("세션 정보: email=" + session.getAttribute("email"));

            return ResponseEntity.ok("Login successful");
        } catch (ResponseStatusException e) {
            // 인증 실패 시 HTTP 401 반환
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            // 세션 가져오기
            HttpSession session = request.getSession(false);  // 이미 세션이 있다면 가져오고, 없으면 null 반환

            if (session != null) {
                // 세션에서 사용자 정보 제거
                session.invalidate();  // 세션을 무효화하여 로그아웃 처리
                log.info("세션 종료 및 로그아웃 처리 완료");
            }

            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }
}
