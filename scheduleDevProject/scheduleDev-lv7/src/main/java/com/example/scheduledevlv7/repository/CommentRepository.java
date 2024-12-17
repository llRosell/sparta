package com.example.scheduledevlv7.repository;

import com.example.scheduledevlv7.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    // 일정별, 사용자별 댓글 조회
    List<Comment> findByScheduleIdAndUserId(Long scheduleId, Long userId);
}