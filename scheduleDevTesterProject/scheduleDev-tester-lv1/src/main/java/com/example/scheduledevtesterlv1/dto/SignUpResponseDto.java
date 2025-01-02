package com.example.scheduledevtesterlv1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {

    // 사용자 이메일: 가입 요청 시 반환되는 사용자의 이메일
    private String email;

    // 메시지: 응답 메시지로, 회원가입 처리 후 성공 또는 오류 메시지를 담습니다
    private String message;

    // 생성자: 메시지만 받는 생성자 (주로 성공 또는 실패 메시지만 전달)
    public SignUpResponseDto(String message) {
        this.message = message;
    }

}
