package scheduleapp.repository;

import scheduleapp.dto.ScheduleResponseDto;
import scheduleapp.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    // 일정 저장
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 모든 일정 조회
    List<ScheduleResponseDto> getAllSchedules();

    // 특정 일정 조회 (ID로 조회)
    Optional<ScheduleResponseDto> getScheduleById(Long id); // 반환 타입을 ScheduleResponseDto로 수정
}
