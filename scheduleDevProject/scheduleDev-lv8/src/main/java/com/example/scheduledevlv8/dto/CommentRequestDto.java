package com.example.scheduledevlv8.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long userId;
    private Long scheduleId;
    private String content;

}
