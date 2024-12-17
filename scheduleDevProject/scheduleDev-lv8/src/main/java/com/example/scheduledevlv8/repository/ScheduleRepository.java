package com.example.scheduledevlv8.repository;

import com.example.scheduledevlv8.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 사용자 ID로 일정 조회
    List<Schedule> findByUserId(Long id);

    // userId를 기준으로 일정 데이터를 수정일 기준 내림차순으로 페이징 처리
    Page<Schedule> findByUserIdOrderByModifiedAtDesc(Long userId, Pageable pageable);

}
