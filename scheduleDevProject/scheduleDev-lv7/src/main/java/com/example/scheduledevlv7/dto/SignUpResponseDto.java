package com.example.scheduledevlv7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {

    private String email;
    private String message;

    public SignUpResponseDto(String message) {
        this.message = message;
    }

}
