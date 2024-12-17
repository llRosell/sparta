package com.example.scheduledevlv2.repository;

import com.example.scheduledevlv2.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 사용자 ID로 일정 조회
    default Schedule findScheduleByUserId(Long userId) {
        return findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT, "Does not exist id = " + userId));  // id -> userId로 수정
    }
}
