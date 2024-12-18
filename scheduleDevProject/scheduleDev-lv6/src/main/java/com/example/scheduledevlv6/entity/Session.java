package com.example.scheduledevlv6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 세션 엔티티의 고유 ID

    @Column(nullable = false, unique = true)
    private String sessionId;  // 세션 ID (서버에서 생성된 세션 ID)

    @Column(nullable = false)
    private String email;  // 사용자 이메일 (세션과 연결된 사용자 정보)

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 세션 생성 시간

}

