package com.example.scheduledevlv3.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "scheduledev")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 ID

    @Column(nullable = false)
    private String title; // 할 일 제목

    @Column(columnDefinition = "longtext")
    private String contents; // 할 일 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // User 객체를 참조하는 필드

    // 비밀번호는 User의 비밀번호를 사용하도록 수정
    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    // 기본 생성자 및 생성자, getter, setter 등
    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
