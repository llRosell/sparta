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
    private final PasswordEncryptionService passwordEncryptionService; // 비밀번호 암호화 서비스 주입

    /**
     * 회원가입 API
     * 회원가입 요청을 처리하고, 이메일 중복 확인 및 비밀번호 암호화를 수행합니다.
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 처리 결과
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        try {
            // 사용자가 입력한 비밀번호를 암호화
            String encryptedPassword = passwordEncryptionService.encodePassword(requestDto.getPassword());

            // 이메일 중복 체크 후 회원가입 처리
            boolean isSignUpSuccessful = loginService.signUp(requestDto.getEmail(), encryptedPassword);

            if (!isSignUpSuccessful) {
                // 이메일이 이미 존재할 경우, 중복된 이메일에 대한 처리
                log.error("회원가입 실패: 이미 존재하는 이메일입니다. email={}", requestDto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDto("Email already exists"));
            }

            // 회원가입 성공 시, 성공 메시지 반환
            return new ResponseEntity<>(
                    new SignUpResponseDto(requestDto.getEmail(), "Signup successful"),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            // 회원가입 중 예외가 발생할 경우, 서버 오류 처리
            log.error("회원가입 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignUpResponseDto("Internal server error"));
        }
    }

    /**
     * 세션 로그인 API
     * 사용자의 이메일과 비밀번호를 통해 로그인 인증을 수행합니다. 인증 성공 시 세션에 사용자 정보를 저장합니다.
     * @param requestDto 로그인 요청 정보
     * @param request HTTP 요청 객체 (세션 정보 포함)
     * @return 로그인 처리 결과
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

            log.info("세션 정보: email=" + session.getAttribute("email"));

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            // 인증 실패 시, 잘못된 자격 증명 처리
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    /**
     * 로그아웃 API
     * 현재 세션을 무효화하여 로그아웃 처리합니다.
     * @param request HTTP 요청 객체 (세션 정보 포함)
     * @return 로그아웃 처리 결과
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            // 현재 세션 정보 가져오기 (세션이 없으면 null 반환)
            HttpSession session = request.getSession(false);

            if (session != null) {
                // 세션이 존재하면 세션 무효화 처리 (로그아웃)
                session.invalidate();
                log.info("세션 종료 및 로그아웃 처리 완료");
            }

            // 로그아웃 성공 메시지 반환
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            // 로그아웃 중 예외가 발생하면 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }
}
