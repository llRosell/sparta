package com.example.scheduledevlv4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SignUpRequestDto {

    private final String email;
    private final String password;

}
