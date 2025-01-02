package com.example.scheduledevtesterlv2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCommentEntity {

    // 생성일자: 엔티티가 처음 생성된 시점을 자동으로 저장
    // updatable = false: 생성일자는 수정되지 않도록 설정
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일자: 엔티티가 수정될 때마다 자동으로 업데이트되는 시점
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
