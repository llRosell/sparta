package com.example.scheduledevlv8.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long id; // 댓글의 고유 ID
    private String content; // 댓글 내용
    private Long userId; // 댓글 작성자 유저의 ID
    private Long scheduleId; // 댓글이 달린 일정의 ID
    private LocalDateTime createdAt; // 댓글 생성 시간
    private LocalDateTime modifiedAt; // 댓글 수정 시간

}
