package com.example.scheduledevtesterlv1.controller;

import com.example.scheduledevtesterlv1.dto.SignUpRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BeanValidationController {

    /**
     * 회원가입 요청을 처리하는 API 엔드포인트입니다.
     * 클라이언트에서 전송한 JSON 데이터를 `SignUpRequestDto` 객체로 바인딩하고,
     * 유효성 검사를 진행한 후, 오류가 있을 경우 오류 메시지를 반환합니다.
     *
     * @param dto 회원가입 요청에 대한 DTO
     * @param bindingResult 유효성 검사 결과
     * @return 오류 메시지 또는 회원가입 성공 메시지
     */
    @PostMapping("/error-message") // '/error-message' 경로로 POST 요청을 처리
    public String beanValidation(
            @Validated @RequestBody SignUpRequestDto dto, // 유효성 검사 어노테이션과 함께 요청 본문을 DTO로 변환
            BindingResult bindingResult // 유효성 검사 결과를 담는 객체
    ) {
        // 유효성 검사에서 오류가 발생한 경우
        if (bindingResult.hasErrors()) {
            // 필드 오류가 있을 경우, 첫 번째 오류 메시지를 문자열로 반환
            return String.valueOf(bindingResult.getFieldError());
        }

        // 유효성 검사 통과 시 회원가입 성공 메시지 반환
        return "회원가입 성공";
    }
}
