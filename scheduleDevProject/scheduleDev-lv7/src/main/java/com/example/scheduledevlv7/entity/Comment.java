package com.example.scheduledevlv7.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends BaseCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String content; // 댓글 내용

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "schedule_id") // 유저 고유 식별자// 일정 고유 식별자
    private Schedule schedule; // 일정과의 연관 관계 (일정 고유 식별자)

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id") // 유저 고유 식별자
    private User user;  // User 객체를 참조하는 필드

}
