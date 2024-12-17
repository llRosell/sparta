package com.example.scheduledevlv8.service;

import com.example.scheduledevlv8.entity.Comment;
import com.example.scheduledevlv8.repository.CommentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;  // CommentRepository 의존성 주입

    /**
     * 댓글 생성 메서드
     * @param comment 생성할 댓글 객체
     * @return 저장된 댓글 객체
     */
    public Comment createComment(Comment comment) {
        // Repository가 null인 경우 로깅
        if (commentRepository == null) {
            log.error("CommentRepository가 주입되지 않았습니다.");  // Repository 미주입 시 에러 로그
        }
        // 댓글 저장 후 반환
        return commentRepository.save(comment);
    }

    /**
     * 일정과 유저에 해당하는 댓글을 조회하는 메서드
     * @param scheduleId 일정 ID
     * @param userId 유저 ID
     * @return 해당 일정과 유저에 해당하는 댓글 리스트
     */
    public List<Comment> getCommentsByScheduleAndUser(Long scheduleId, Long userId) {
        // 댓글 조회
        List<Comment> comments = commentRepository.findByScheduleIdAndUserId(scheduleId, userId);

        // 댓글이 없으면 빈 리스트 반환하고 로그 기록
        if (comments == null) {
            log.error("댓글이 없습니다. scheduleId: {}, userId: {}", scheduleId, userId);  // 댓글이 없는 경우 로그
            return Collections.emptyList();  // 빈 리스트 반환
        }

        // 조회된 댓글 반환
        return comments;
    }

    /**
     * 댓글을 수정하는 메서드
     * @param commentId 수정할 댓글의 ID
     * @param userId 댓글을 수정할 유저의 ID
     * @param scheduleId 댓글이 속한 일정의 ID
     * @param newContent 수정할 댓글 내용
     * @return 수정된 댓글 객체
     */
    public Comment updateComment(Long commentId, Long userId, Long scheduleId, String newContent) {
        // 개행 문자 처리: 모든 개행을 공백으로 변경
        newContent = newContent.replaceAll("[\r\n]+", " ");  // 개행 문자 제거

        // 만약 newContent가 JSON 형식으로 감싸져 있다면 이를 파싱
        try {
            ObjectMapper objectMapper = new ObjectMapper();  // JSON 파서를 위한 ObjectMapper
            JsonNode jsonNode = objectMapper.readTree(newContent);  // newContent 파싱
            newContent = jsonNode.get("content").asText();  // 실제 content 값만 추출
        } catch (Exception e) {
            // JSON 파싱 오류 발생 시 예외 처리
            throw new RuntimeException("댓글 내용 파싱 중 오류가 발생했습니다.");  // 예외 발생 시 메시지 반환
        }

        // 댓글을 DB에서 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));  // 댓글이 없으면 예외 발생

        // 댓글의 사용자 및 일정 검증
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("사용자가 이 댓글을 수정할 권한이 없습니다.");  // 유저 권한 검증
        }

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new RuntimeException("일정이 일치하지 않습니다.");  // 일정 검증
        }

        // 댓글 내용 수정
        comment.setContent(newContent);

        // 수정된 댓글 저장
        return commentRepository.save(comment);  // 수정된 댓글 DB에 저장
    }

    /**
     * 댓글을 삭제하는 메서드
     * @param commentId 삭제할 댓글의 ID
     */
    public void deleteComment(Long commentId) {
        // 댓글 삭제
        commentRepository.deleteById(commentId);  // DB에서 댓글 삭제
    }
}