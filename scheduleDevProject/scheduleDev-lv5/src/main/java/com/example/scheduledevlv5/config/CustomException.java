package com.example.scheduledevlv5.config; // 패키지 경로는 프로젝트 구조에 맞게 조정하세요.

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드 설정
public class CustomException extends RuntimeException {
    private String message;

    public CustomException(String message, HttpStatus badRequest) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
