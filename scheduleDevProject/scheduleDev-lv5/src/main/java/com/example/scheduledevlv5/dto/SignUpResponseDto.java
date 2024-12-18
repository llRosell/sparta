package com.example.scheduledevlv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponseDto {

    private String email;
    private String message;

    public SignUpResponseDto(String message) {
        this.message = message;
    }

}
