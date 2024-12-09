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

    // Schedule 엔티티 조회(비밀번호 맵핑)
    Optional<Schedule> getScheduleByIdEntity(Long id);

    // 일정 수정 (할 일, 이름)
    int updateSchedule(Schedule schedule);

    // 일정 삭제
    void deleteSchedule(Long id);
}
