package com.example.scheduledevlv7.service;

import com.example.scheduledevlv7.entity.Comment;
import com.example.scheduledevlv7.repository.CommentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        if (commentRepository == null) {
            log.error("CommentRepository가 주입되지 않았습니다.");
        }
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByScheduleAndUser(Long scheduleId, Long userId) {
        // 예외 처리를 추가하여 데이터가 없는 경우 빈 리스트 반환
        List<Comment> comments = commentRepository.findByScheduleIdAndUserId(scheduleId, userId);
        if (comments == null) {
            log.error("댓글이 없습니다. scheduleId: {}, userId: {}", scheduleId, userId);
            return Collections.emptyList();
        }
        return comments;
    }

    // 댓글 수정
    public Comment updateComment(Long commentId, Long userId, Long scheduleId, String newContent) {
        // 개행 문자 처리
        newContent = newContent.replaceAll("[\r\n]+", " ");

        // 만약 newContent가 JSON 형식으로 감싸져 있다면 이를 파싱
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(newContent);
            newContent = jsonNode.get("content").asText(); // 실제 content 값만 추출
        } catch (Exception e) {
            // JSON 파싱 오류 처리
            throw new RuntimeException("댓글 내용 파싱 중 오류가 발생했습니다.");
        }

        // 댓글을 DB에서 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // User와 Schedule 검증
        if (!comment.getUser().getId().equals(userId)) {
            throw new RuntimeException("사용자가 이 댓글을 수정할 권한이 없습니다.");
        }

        if (!comment.getSchedule().getId().equals(scheduleId)) {
            throw new RuntimeException("일정이 일치하지 않습니다.");
        }

        // 댓글 내용 수정
        comment.setContent(newContent);

        // 수정된 댓글 저장
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

