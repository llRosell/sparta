package com.example.scheduledevtesterlv1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login")
public class Login {

    @Id
    @Column(nullable = false)
    private String email;  // 로그인에 사용되는 이메일 주소

    @Column(nullable = false)
    private String password;  // 사용자의 로그인 비밀번호
}
