package com.example.scheduledevlv7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "제목은 10글자 이내로 입력해주세요.")
    private String title;

    @NotNull(message = "내용은 필수입니다.")
    private String contents;

}