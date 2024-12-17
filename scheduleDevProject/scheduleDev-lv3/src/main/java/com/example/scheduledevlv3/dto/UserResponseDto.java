package com.example.scheduledevlv3.dto;

import com.example.scheduledevlv3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private final String username;
    private final String email;

    public UserResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // User 객체를 받아서 값을 초기화하는 생성자
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    // 정적 메서드: User 객체를 UserResponseDto로 변환
    public static UserResponseDto toUserDto(User user){
        return new UserResponseDto(user);  // User 객체를 받아 UserResponseDto로 변환
    }
}