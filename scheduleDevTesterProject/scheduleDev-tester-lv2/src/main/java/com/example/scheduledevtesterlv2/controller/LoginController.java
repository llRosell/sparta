package com.example.scheduledevtesterlv2.controller;

import com.example.scheduledevtesterlv2.dto.SignUpRequestDto;
import com.example.scheduledevtesterlv2.dto.SignUpResponseDto;
import com.example.scheduledevtesterlv2.entity.Login;
import com.example.scheduledevtesterlv2.service.LoginService;
import com.example.scheduledevtesterlv2.service.PasswordEncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final PasswordEncryptionService passwordEncryptionService; // 비밀번호 암호화 서비스 주입

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        String encryptedPassword = passwordEncryptionService.encodePassword(requestDto.getPassword());
        boolean isSignUpSuccessful = loginService.signUp(requestDto.getEmail(), encryptedPassword);

        if (!isSignUpSuccessful) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new SignUpResponseDto("Email already exists"));
        }

        return new ResponseEntity<>(
                new SignUpResponseDto(requestDto.getEmail(), "Signup successful"),
                HttpStatus.CREATED);
    }

    /**
     * 세션 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignUpRequestDto requestDto, HttpServletRequest request) {
        // 이메일과 비밀번호를 통해 로그인 인증 수행
        Login login = loginService.authenticateUser(requestDto.getEmail(), requestDto.getPassword());

        // 로그인 성공 시 세션에 사용자 이메일 저장
        HttpSession session = request.getSession(true);  // 세션이 없으면 새로 생성
        session.setAttribute("email", requestDto.getEmail());

        // 세션 ID를 MySQL에 저장
        loginService.storeSession(session.getId(), requestDto.getEmail());

        log.info("세션 정보: email=" + session.getAttribute("email"));

        return ResponseEntity.ok("Login successful");
    }

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("세션 종료 및 로그아웃 처리 완료");
        }
        return ResponseEntity.ok("Logout successful");
    }

    /**
     * 사용자 정보 조회 API
     */
    @GetMapping("/info")
    public ResponseEntity<Login> getUserInfo(HttpServletRequest request) {
        // 세션 ID를 헤더에서 가져옴 (예: "Authorization" 헤더)
        String sessionId = request.getHeader("Authorization");

        if (sessionId == null || sessionId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session ID is missing");
        }

        // 세션 ID로 사용자 인증
        Login authenticatedUser = loginService.authenticateUserBySession(sessionId);

        // 인증된 사용자 정보를 반환
        return ResponseEntity.ok(authenticatedUser);
    }
}
