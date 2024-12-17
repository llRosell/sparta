package com.example.scheduledevlv3.dto;

import com.example.scheduledevlv3.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final Long userId;  // User의 ID를 Long으로 처리

    // 정적 메서드: Schedule 객체를 ScheduleResponseDto로 변환
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.userId = schedule.getUser().getId();  // User에서 userId를 가져옵니다
    }
}
