package com.example.scheduledevtesterlv1.dto;

import com.example.scheduledevtesterlv1.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    // 일정의 고유 ID
    private final Long id;

    // 일정의 제목
    private final String title;

    // 일정의 상세 내용
    private final String contents;

    // 일정에 달린 댓글의 개수
    private final int commentsCount;

    // 일정이 생성된 시간
    private LocalDateTime createdAt;

    // 일정이 수정된 시간
    private LocalDateTime modifiedAt;

    // 일정 작성자의 User ID
    private final Long userId;

    // Schedule 객체를 ScheduleResponseDto로 변환하는 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();  // Schedule의 ID
        this.title = schedule.getTitle();  // Schedule의 제목
        this.contents = schedule.getContents();  // Schedule의 내용
        // 댓글의 개수를 가져오는데, 댓글이 null이면 0으로 처리
        this.commentsCount = schedule.getComments() != null ? schedule.getComments().size() : 0;
        this.createdAt = schedule.getCreatedAt();  // 일정 생성 시간
        this.modifiedAt = schedule.getModifiedAt();  // 일정 수정 시간
        this.userId = schedule.getUser().getId();  // 일정 작성자의 User ID
    }
}
