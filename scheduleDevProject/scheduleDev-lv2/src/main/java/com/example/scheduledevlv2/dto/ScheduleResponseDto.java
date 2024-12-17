package com.example.scheduledevlv2.dto;

import com.example.scheduledevlv2.entity.Schedule;
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
    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser().getId());  // user.getId()로 userId 설정
    }
}
