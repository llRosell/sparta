package com.example.scheduledevtesterlv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    // 일정 작성자 유저의 고유 ID
    private Long userId;

    // 일정 제목 (필수, 최대 10글자)
    @NotBlank(message = "제목은 필수입니다.")  // 제목이 비어있지 않아야 함
    @Size(max = 10, message = "제목은 10글자 이내로 입력해주세요.")  // 제목은 10글자를 넘을 수 없음
    private String title;

    // 일정 내용 (필수)
    @NotNull(message = "내용은 필수입니다.")  // 내용은 null일 수 없음
    private String contents;

}
