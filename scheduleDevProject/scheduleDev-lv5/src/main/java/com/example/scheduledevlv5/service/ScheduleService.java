package com.example.scheduledevlv5.service;

import com.example.scheduledevlv5.dto.ScheduleRequestDto;
import com.example.scheduledevlv5.dto.ScheduleResponseDto;
import com.example.scheduledevlv5.entity.Schedule;
import com.example.scheduledevlv5.entity.User;
import com.example.scheduledevlv5.repository.ScheduleRepository;
import com.example.scheduledevlv5.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 저장
    @Transactional
    public ScheduleResponseDto save(Long userId, ScheduleRequestDto dto) {
        // User 객체를 참조하는 부분
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Schedule 객체 생성 및 사용자 할당
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());
        schedule.setUser(user);  // User 객체를 세팅

        schedule = scheduleRepository.save(schedule); // 저장 후 응답 반환
        return new ScheduleResponseDto(schedule);
    }

    // 특정 일정 조회
    public ScheduleResponseDto findScheduleByUserId(Long id, Long UserId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        return new ScheduleResponseDto(schedule);
    }

    // 모든 일정 조회
    public List<ScheduleResponseDto> getAllSchedules(Long userId) {
        // 해당 userId에 속한 모든 일정을 가져옴
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)  // Schedule 객체를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 일정 업데이트
    @Transactional
    public ScheduleResponseDto update(Long id, Long userId, ScheduleRequestDto dto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        schedule = scheduleRepository.save(schedule); // 업데이트 반영
        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id, Long userId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
