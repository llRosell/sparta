package com.example.scheduledevlv6.dto;

import com.example.scheduledevlv6.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto extends User {

    private Long id;
    private final String username;
    private final String email;
    private LocalDateTime createdAt;

    // User 엔티티를 기반으로 변환할 수 있는 생성자 추가
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }
}