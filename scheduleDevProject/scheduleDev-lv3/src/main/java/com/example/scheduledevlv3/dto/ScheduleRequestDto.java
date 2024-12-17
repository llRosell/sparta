package com.example.scheduledevlv3.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long userId;
    private String title;
    private String contents;
    private String password;

}