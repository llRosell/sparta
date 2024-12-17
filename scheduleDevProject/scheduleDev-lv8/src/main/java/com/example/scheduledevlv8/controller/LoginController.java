package com.example.scheduledevlv8.controller;

import com.example.scheduledevlv8.dto.SignUpRequestDto;
import com.example.scheduledevlv8.dto.SignUpResponseDto;
import com.example.scheduledevlv8.entity.Login;
import com.example.scheduledevlv8.service.LoginService;
import com.example.scheduledevlv8.service.PasswordEncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    private final LoginService loginService;
    private final PasswordEncryptionService passwordEncryptionService; // 생성자 주입을 통해 자동 주입

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        try {
            // 비밀번호 암호화
            String encryptedPassword = passwordEncryptionService.encodePassword(requestDto.getPassword());

            // 회원가입 처리 - 이메일 중복 체크 후 처리
            boolean isSignUpSuccessful = loginService.signUp(requestDto.getEmail(), encryptedPassword);

            if (!isSignUpSuccessful) {
                // 이메일이 이미 존재하는 경우 처리
                log.error("회원가입 실패: 이미 존재하는 이메일입니다. email={}", requestDto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDto("Email already exists"));
            }

            return new ResponseEntity<>(
                    new SignUpResponseDto(requestDto.getEmail(), "Signup successful"),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("회원가입 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignUpResponseDto("Internal server error"));
        }
    }

    // 세션 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignUpRequestDto requestDto, HttpServletRequest request) {
        try {
            // 이메일과 비밀번호를 authenticateUser 메소드에 전달
            Login login = loginService.authenticateUser(requestDto.getEmail(), requestDto.getPassword());

            if (login == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }

            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession(true);
            session.setAttribute("email", requestDto.getEmail());

            log.info("세션 정보: email=" + session.getAttribute("email"));

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
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
