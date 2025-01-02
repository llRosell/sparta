package com.example.scheduledevtesterlv2.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * CustomException은 사용자 정의 예외 클래스입니다.
 * 이 클래스는 HTTP 상태 코드 BAD_REQUEST(400)을 반환하도록 설정되어 있습니다.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드 BAD_REQUEST(400)을 설정하여 예외 발생 시 클라이언트에 400 상태 코드를 응답으로 반환
public class CustomException extends RuntimeException {
    private String message; // 예외 메시지를 저장할 필드

    /**
     * 생성자: CustomException을 생성합니다.
     * @param message 예외 메시지
     * @param badRequest HTTP 상태 코드
     */
    public CustomException(String message, HttpStatus badRequest) {
        super(message); // 부모 클래스인 RuntimeException에 메시지를 전달하여 예외 처리
        this.message = message; // 메시지 필드에 예외 메시지를 저장
    }

    /**
     * 예외 메시지를 반환하는 메서드
     * @return 예외 메시지
     */
    public String getMessage() {
        return message; // 예외 메시지를 반환
    }
}
