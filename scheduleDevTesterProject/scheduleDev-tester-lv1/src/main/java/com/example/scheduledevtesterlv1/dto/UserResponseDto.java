package com.example.scheduledevtesterlv1.dto;

import com.example.scheduledevtesterlv1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto extends User {

    // 유저 ID: 사용자 고유의 ID 값을 저장하는 필드
    private Long id;

    // 유저명: 사용자의 이름 또는 유저명을 나타내는 필드
    private final String username;

    // 이메일: 사용자의 이메일 주소를 나타내는 필드
    private final String email;

    // 생성일: 사용자가 생성된 날짜와 시간을 나타내는 필드
    private LocalDateTime createdAt;

    // User 엔티티를 기반으로 변환할 수 있는 생성자
    // 이 생성자는 User 엔티티를 받아서 DTO로 변환하는 역할을 수행합니다.
    public UserResponseDto(User user) {
        this.id = user.getId();  // User 엔티티에서 ID를 가져옴
        this.username = user.getUsername();  // User 엔티티에서 유저명을 가져옴
        this.email = user.getEmail();  // User 엔티티에서 이메일을 가져옴
        this.createdAt = user.getCreatedAt();  // User 엔티티에서 생성일자를 가져옴
    }
}
