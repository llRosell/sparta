package com.example.scheduledevtesterlv2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;  // 유저 고유 식별자 (ID)

    @Column(nullable = false)
    private String username;  // 유저의 이름

    @Column(nullable = false)
    private String email;  // 유저의 이메일 주소

    // 유저 생성자: username과 email을 받는 생성자
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
