package scheduleapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import scheduleapp.dto.ScheduleRequestDto;
import scheduleapp.dto.ScheduleResponseDto;
import scheduleapp.entity.Schedule;
import scheduleapp.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTodo(), dto.getName(), dto.getPassword());
        schedule.setCreated(LocalDateTime.now());
        schedule.setUpdated(LocalDateTime.now());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.getAllSchedules();
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) { // 인자 타입을 Long으로 수정
        Optional<ScheduleResponseDto> optionalSchedule = scheduleRepository.getScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }

        return optionalSchedule.get(); // ScheduleResponseDto를 직접 반환
    }
}
