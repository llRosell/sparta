package com.example.scheduledevtesterlv2.entity;

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
@Entity  // 이 클래스는 JPA 엔티티로 매핑되어 데이터베이스 테이블과 연결됩니다.
@Table(name = "comments")  // 해당 엔티티는 "comments" 테이블과 매핑됩니다.
public class Comment extends BaseCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글의 고유 ID

    @Column(columnDefinition = "longtext")
    private String content; // 댓글 내용

    @ManyToOne  // 여러 댓글이 하나의 일정과 연관될 수 있으므로, 일정과의 다대일 관계 설정
    @JsonIgnore
    @JoinColumn(name = "schedule_id")
    private Schedule schedule; // 해당 댓글이 달린 일정 (일정과의 연관 관계)

    @ManyToOne  // 여러 댓글이 하나의 사용자와 연관될 수 있으므로, 사용자와의 다대일 관계 설정
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;  // 댓글을 작성한 사용자 (사용자와의 연관 관계)
}
