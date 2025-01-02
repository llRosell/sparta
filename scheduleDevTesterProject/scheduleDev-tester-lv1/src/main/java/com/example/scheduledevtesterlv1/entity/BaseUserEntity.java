package com.example.scheduledevtesterlv1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUserEntity {

    // 생성일자: 엔티티가 처음 생성될 때 자동으로 기록되는 날짜와 시간
    // updatable = false: 생성일자는 수정되지 않도록 설정
    // nullable = false: 이 필드는 null 값을 허용하지 않음
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

}
