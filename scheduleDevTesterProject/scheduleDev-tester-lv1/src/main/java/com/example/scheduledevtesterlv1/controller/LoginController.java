package com.example.scheduledevtesterlv1.controller;

import com.example.scheduledevtesterlv1.dto.SignUpRequestDto;
import com.example.scheduledevtesterlv1.dto.SignUpResponseDto;
import com.example.scheduledevtesterlv1.entity.Login;
import com.example.scheduledevtesterlv1.service.LoginService;
import com.example.scheduledevtesterlv1.service.PasswordEncryptionService;
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
        try {
            String encryptedPassword = passwordEncryptionService.encodePassword(requestDto.getPassword());
            boolean isSignUpSuccessful = loginService.signUp(requestDto.getEmail(), encryptedPassword);

            if (!isSignUpSuccessful) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDto("Email already exists"));
            }

            return new ResponseEntity<>(
                    new SignUpResponseDto(requestDto.getEmail(), "Signup successful"),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignUpResponseDto("Internal server error"));
        }
    }

    /**
     * 세션 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignUpRequestDto requestDto, HttpServletRequest request) {
        try {
            // 이메일과 비밀번호를 통해 로그인 인증 수행
            Login login = loginService.authenticateUser(requestDto.getEmail(), requestDto.getPassword());

            if (login == null) {
                // 인증 실패 시, 권한 없음 처리
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }

            // 로그인 성공 시 세션에 사용자 이메일 저장
            HttpSession session = request.getSession(true);  // 세션이 없으면 새로 생성
            session.setAttribute("email", requestDto.getEmail());

            // 세션 ID를 MySQL에 저장
            loginService.storeSession(session.getId(), requestDto.getEmail());

            log.info("세션 정보: email=" + session.getAttribute("email"));

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            // 인증 실패 시, 잘못된 자격 증명 처리
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                log.info("세션 종료 및 로그아웃 처리 완료");
            }
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }

    /**
     * 사용자 정보 조회 API
     *
     * 이 메서드는 클라이언트로부터 전달된 세션 ID를 사용하여
     * 현재 인증된 사용자의 정보를 반환합니다.
     *
     * 요청 헤더에 포함된 "Authorization" 값을 통해 세션 ID를 확인하며,
     * 세션 ID가 유효하지 않거나 만료된 경우 인증 실패를 반환합니다.
     *
     * @param request 클라이언트의 HTTP 요청 객체
     *                요청 헤더에서 "Authorization" 키로 세션 ID를 전달받습니다.
     * @return 인증된 사용자 정보(Login 객체)를 HTTP 응답으로 반환
     * @throws ResponseStatusException 세션 ID가 없거나 유효하지 않을 경우 401 Unauthorized 에러를 반환
     */
    @GetMapping("/info")
    public ResponseEntity<Login> getUserInfo(HttpServletRequest request) {
        // 세션 ID를 헤더에서 가져옴 (예: "Authorization" 헤더)
        String sessionId = request.getHeader("Authorization");

        if (sessionId == null || sessionId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Session ID is missing");
        }

        // 세션으로 사용자 인증
        Login authenticatedUser = loginService.authenticateUserBySession(sessionId);

        return ResponseEntity.ok(authenticatedUser); // 인증된 사용자 정보 반환
    }
}
