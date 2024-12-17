package com.example.scheduledevlv7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long userId;
    private Long scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}