package com.example.scheduledevlv8.dto;

import com.example.scheduledevlv8.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final int commentsCount;  // 댓글 개수를 위한 필드 추가
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private final Long userId;  // User의 ID를 Long으로 처리


    // 정적 메서드: Schedule 객체를 ScheduleResponseDto로 변환
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.commentsCount = schedule.getComments() != null ? schedule.getComments().size() : 0;  // 댓글 개수 가져오기
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.userId = schedule.getUser().getId();  // User에서 userId를 가져옵니다
    }
}
