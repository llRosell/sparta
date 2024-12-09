package scheduleapp.service;

import scheduleapp.dto.ScheduleRequestDto;
import scheduleapp.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getAllSchedules();

    ScheduleResponseDto getScheduleById(Long id); // 인자 타입을 Long으로 수정

    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

    void deleteSchedule(Long id, ScheduleRequestDto dto);

}
