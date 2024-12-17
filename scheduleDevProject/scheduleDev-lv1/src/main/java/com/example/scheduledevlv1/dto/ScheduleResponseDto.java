package com.example.scheduledevlv1.dto;

import com.example.scheduledevlv1.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private final String username;
    private final String title;
    private final String contents;


    // 정적 메서드: Schedule 객체를 ScheduleResponseDto로 변환
    public static ScheduleResponseDto toDto (Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUsername(),
                schedule.getTitle(),
                schedule.getContents()
        );
    }
}
