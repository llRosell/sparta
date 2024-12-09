package scheduleapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public ScheduleResponseDto getScheduleById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.getScheduleByIdEntity(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }

        // Schedule을 ScheduleResponseDto로 변환하여 반환
        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        // ID로 스케줄 조회
        Optional<Schedule> optionalSchedule = scheduleRepository.getScheduleByIdEntity(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();

        // 비밀번호 검증
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 필드 업데이트
        schedule.setTodo(dto.getTodo());
        schedule.setName(dto.getName());
        schedule.setUpdated(LocalDateTime.now());

        // 데이터베이스에 업데이트 반영
        scheduleRepository.updateSchedule(schedule);

        // 업데이트된 ScheduleResponseDto 반환
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto dto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.getScheduleByIdEntity(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();

        // 비밀번호 검증
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 삭제
        scheduleRepository.deleteSchedule(id);
    }
}
