package com.example.scheduledevlv1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "scheduledev")
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 ID

    @Column(nullable = false)
    private String username; // 유저명

    @Column(nullable = false)
    private String title; // 할 일 제목

    @Column(columnDefinition = "longtext")
    private String contents; // 할 일 내용

    public Schedule(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
