package com.example.scheduledevtesterlv1.controller;

import com.example.scheduledevtesterlv1.dto.CommentRequestDto; // 댓글 생성 요청 DTO
import com.example.scheduledevtesterlv1.dto.CommentResponseDto; // 댓글 응답 DTO
import com.example.scheduledevtesterlv1.entity.Comment; // 댓글 엔티티
import com.example.scheduledevtesterlv1.entity.Schedule; // 일정 엔티티
import com.example.scheduledevtesterlv1.entity.User; // 사용자 엔티티
import com.example.scheduledevtesterlv1.repository.CommentRepository; // 댓글 리포지토리
import com.example.scheduledevtesterlv1.repository.UserRepository; // 사용자 리포지토리
import com.example.scheduledevtesterlv1.service.CommentService; // 댓글 서비스
import com.example.scheduledevtesterlv1.repository.ScheduleRepository; // 일정 리포지토리

import lombok.RequiredArgsConstructor; // 의존성 주입을 위한 어노테이션
import lombok.extern.slf4j.Slf4j; // SLF4J 로깅을 위한 어노테이션
import org.springframework.web.bind.annotation.*; // Spring Web 관련 어노테이션

import java.util.List; // List 인터페이스
import java.util.stream.Collectors; // 스트림을 활용하여 리스트 변환

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 댓글을 생성하는 API 엔드포인트입니다.
     * 클라이언트로부터 받은 댓글 정보를 바탕으로 댓글을 생성하고, 생성된 댓글의 정보를 응답으로 반환합니다.
     *
     * @param commentRequestDto 클라이언트가 보낸 댓글 생성 요청 DTO
     * @return 생성된 댓글의 정보를 담은 CommentResponseDto
     */
    @PostMapping // HTTP POST 요청을 처리
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto) {
        Long userId = commentRequestDto.getUserId(); // 사용자 ID
        Long scheduleId = commentRequestDto.getScheduleId(); // 일정 ID
        String content = commentRequestDto.getContent(); // 댓글 내용

        // 유효성 검사: 필수 값이 비어있으면 오류 발생
        if (userId == null || scheduleId == null || content == null) {
            throw new RuntimeException("잘못된 입력 값입니다.");
        }

        // 일정과 사용자 객체 조회
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 댓글 객체 생성 및 설정
        Comment comment = new Comment();
        comment.setSchedule(schedule);
        comment.setUser(user);
        comment.setContent(content);

        try {
            // 댓글 생성 서비스 호출
            Comment savedComment = commentService.createComment(comment);
            // 댓글 생성 후, 생성된 댓글 정보를 DTO로 변환하여 반환
            return new CommentResponseDto(
                    savedComment.getId(),
                    savedComment.getContent(),
                    savedComment.getUser().getId(),
                    savedComment.getSchedule().getId(),
                    savedComment.getCreatedAt(),
                    savedComment.getModifiedAt()
            );
        } catch (Exception e) {
            log.error("댓글 생성 중 오류가 발생했습니다.", e); // 오류 발생 시 로그 출력
            throw new RuntimeException("댓글 생성 중 오류가 발생했습니다.", e); // 오류 처리
        }
    }

    /**
     * 일정별, 사용자별 댓글을 조회하는 API 엔드포인트입니다.
     * 지정된 일정과 사용자에 대한 댓글 리스트를 반환합니다.
     *
     * @param scheduleId 일정 ID
     * @param userId 사용자 ID
     * @return 조회된 댓글 리스트
     */
    @GetMapping("/schedule/{scheduleId}/user/{userId}") // HTTP GET 요청을 처리
    public List<CommentResponseDto> getCommentsByScheduleAndUser(
            @PathVariable("scheduleId") Long scheduleId, // 경로 변수로 일정 ID
            @PathVariable("userId") Long userId) { // 경로 변수로 사용자 ID

        List<Comment> comments = commentService.getCommentsByScheduleAndUser(scheduleId, userId);

        // 댓글 목록을 CommentResponseDto로 변환하여 반환
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

    /**
     * 댓글을 수정하는 API 엔드포인트입니다.
     * 댓글 내용을 수정하고 수정된 댓글 정보를 반환합니다.
     *
     * @param commentId 댓글 ID
     * @param userId 사용자 ID
     * @param scheduleId 일정 ID
     * @param newContent 수정할 댓글 내용
     * @return 수정된 댓글 정보를 담은 CommentResponseDto
     */
    @PutMapping("/{commentId}/user/{userId}/schedule/{scheduleId}") // HTTP PUT 요청을 처리
    public CommentResponseDto updateComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId,
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody String newContent) {

        // 댓글 수정 서비스 호출
        Comment updatedComment = commentService.updateComment(commentId, userId, scheduleId, newContent);

        // 수정된 댓글 정보 반환
        return new CommentResponseDto(
                updatedComment.getId(),
                updatedComment.getContent(),
                updatedComment.getUser().getId(),
                updatedComment.getSchedule().getId(),
                updatedComment.getCreatedAt(),
                updatedComment.getModifiedAt()
        );
    }

    /**
     * 댓글을 삭제하는 API 엔드포인트입니다.
     * 댓글을 삭제하고 해당 댓글에 대한 삭제 여부를 확인합니다.
     *
     * @param commentId 댓글 ID
     * @param userId 사용자 ID
     * @param scheduleId 일정 ID
     */
    @DeleteMapping("/{commentId}/user/{userId}/schedule/{scheduleId}") // HTTP DELETE 요청을 처리
    public void deleteComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("userId") Long userId,
            @PathVariable("scheduleId") Long scheduleId) {

        // 댓글을 DB에서 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 사용자가 이 댓글을 삭제할 권한이 있는지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("사용자가 이 댓글을 삭제할 권한이 없습니다.");
        }

        // 일정 ID 검증
        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new RuntimeException("일정이 일치하지 않습니다.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }
}
