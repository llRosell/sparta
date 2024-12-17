package com.example.scheduledevlv1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequestDto {

    private String username;
    private String title;
    private String contents;

}