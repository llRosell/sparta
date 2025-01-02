package com.example.scheduledevtesterlv1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private Long userId; // 댓글을 작성한 유저의 ID
    private Long scheduleId; // 댓글이 작성된 일정의 ID
    private String content; // 댓글 내용

}
