package com.example.scheduledevlv2.dto;

import com.example.scheduledevlv2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private final String username;
    private final String email;

    // User 엔티티를 기반으로 UserResponseDto 생성
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
