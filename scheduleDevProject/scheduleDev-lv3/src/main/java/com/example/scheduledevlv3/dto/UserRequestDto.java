package com.example.scheduledevlv3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserRequestDto {

    private final String username;
    private final String email;
    private String password;

}
