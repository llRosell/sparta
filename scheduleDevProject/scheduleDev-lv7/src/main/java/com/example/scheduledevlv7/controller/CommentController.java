package com.example.scheduledevlv7.controller;

import com.example.scheduledevlv7.dto.CommentRequestDto;
import com.example.scheduledevlv7.dto.CommentResponseDto;
import com.example.scheduledevlv7.entity.Comment;
import com.example.scheduledevlv7.entity.Schedule;
import com.example.scheduledevlv7.entity.User;
import com.example.scheduledevlv7.repository.CommentRepository;
import com.example.scheduledevlv7.repository.UserRepository;
import com.example.scheduledevlv7.service.CommentService;
import com.example.scheduledevlv7.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @PostMapping
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto) {
        Long userId = commentRequestDto.getUserId();
        Long scheduleId = commentRequestDto.getScheduleId();
        String content = commentRequestDto.getContent();

        // 유효성 검사
        if (userId == null || scheduleId == null || content == null) {
            throw new RuntimeException("잘못된 입력 값입니다.");
        }

        // Schedule과 User 객체를 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Comment 객체 생성 및 설정
        Comment comment = new Comment();
        comment.setSchedule(schedule);
        comment.setUser(user);
        comment.setContent(content);

        try {
            // 서비스 호출
            Comment savedComment = commentService.createComment(comment);
            // 저장된 댓글을 CommentResponseDto로 변환하여 반환
            return new CommentResponseDto(
                    savedComment.getId(),
                    savedComment.getContent(),
                    savedComment.getUser().getId(),
                    savedComment.getSchedule().getId(),
                    savedComment.getCreatedAt(),
                    savedComment.getModifiedAt()
            );
        } catch (Exception e) {
            log.error("댓글 생성 중 오류가 발생했습니다.", e); // 로그 추가
            throw new RuntimeException("댓글 생성 중 오류가 발생했습니다.", e);
        }
    }

    // 일정별, 사용자별 댓글 조회
    @GetMapping("/schedule/{scheduleId}/user/{userId}")
    public List<CommentResponseDto> getCommentsByScheduleAndUser(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("userId") Long userId) {

        List<Comment> comments = commentService.getCommentsByScheduleAndUser(scheduleId, userId);

        // Comment 객체를 CommentResponseDto로 변환하여 반환
        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getSchedule().getId(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ))
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @PutMapping("/{commentId}/user/{userId}/schedule/{scheduleId}")
    public CommentResponseDto updateComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId,
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody String newContent) {

        Comment updatedComment = commentService.updateComment(commentId, userId, scheduleId, newContent);

        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getContent(),
                updatedComment.getUser().getId(),
                updatedComment.getSchedule().getId(),
                updatedComment.getCreatedAt(),
                updatedComment.getModifiedAt()
        );
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/user/{userId}/schedule/{scheduleId}")
    public void deleteComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId,
            @PathVariable("scheduleId") Long scheduleId) {

        // 댓글을 DB에서 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // User와 Schedule 검증
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("사용자가 이 댓글을 삭제할 권한이 없습니다.");
        }

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new RuntimeException("일정이 일치하지 않습니다.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }
}
