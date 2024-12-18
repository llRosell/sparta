package com.example.scheduledevlv5.controller;

import com.example.scheduledevlv5.dto.SignUpRequestDto;
import com.example.scheduledevlv5.dto.SignUpResponseDto;
import com.example.scheduledevlv5.entity.Login;
import com.example.scheduledevlv5.service.LoginService;
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

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        try {
            // 회원가입 서비스 호출
            boolean isSignUpSuccessful = loginService.signUp(requestDto.getEmail(), requestDto.getPassword());

            if (!isSignUpSuccessful) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new SignUpResponseDto("이미 존재하는 이메일입니다."));
            }

            return new ResponseEntity<>(
                    new SignUpResponseDto(requestDto.getEmail(), "회원가입 성공"),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("회원가입 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SignUpResponseDto("서버 오류"));
        }
    }

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<SignUpResponseDto> login(@Valid @RequestBody SignUpRequestDto requestDto, HttpServletRequest request) {
        try {
            // 이메일과 비밀번호를 통해 로그인 인증 수행
            Login login = loginService.authenticateUser(requestDto.getEmail(), requestDto.getPassword());

            if (login == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 이메일 또는 비밀번호");
            }

            // 로그인 성공 시 세션에 사용자 이메일 저장
            HttpSession session = request.getSession(true);  // 세션이 없으면 새로 생성
            session.setAttribute("email", requestDto.getEmail());

            // 세션 ID를 MySQL에 저장
            loginService.storeSession(session.getId(), requestDto.getEmail());

            log.info("세션 생성 완료: sessionId={}", session.getId());
            log.info("세션에 저장된 이메일: {}", session.getAttribute("email"));

            // 로그인 성공 후 이메일을 포함한 응답 반환
            return ResponseEntity.ok(new SignUpResponseDto(requestDto.getEmail(), "로그인 성공"));

        } catch (Exception e) {
            log.error("로그인 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SignUpResponseDto("인증 실패"));
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
            return ResponseEntity.ok("로그아웃 성공");
        } catch (Exception e) {
            log.error("로그아웃 오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
        }
    }
}
