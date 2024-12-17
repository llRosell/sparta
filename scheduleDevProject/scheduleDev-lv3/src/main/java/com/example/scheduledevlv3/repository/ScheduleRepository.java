package com.example.scheduledevlv3.repository;

import com.example.scheduledevlv3.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 사용자 ID로 일정 조회
    List<Schedule> findByUserId(Long userId);
}
