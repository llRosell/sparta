package com.example.scheduledevtesterlv1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 할일 ID

    @Column(nullable = false)
    private String title; // 할 일 제목

    @Column(columnDefinition = "longtext")
    private String contents; // 할 일 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // User 객체를 참조하는 필드

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments; // 일정에 달린 댓글들
}
