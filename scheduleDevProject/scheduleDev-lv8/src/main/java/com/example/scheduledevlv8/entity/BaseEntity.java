package com.example.scheduledevlv8.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 생성일자: 엔티티가 처음 생성될 때 자동으로 기록되는 날짜와 시간
    // updatable = false: 생성일자는 수정되지 않도록 설정
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수정일자: 엔티티가 수정될 때마다 자동으로 기록되는 날짜와 시간
    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
