package com.example.scheduledevlv2.service;

import com.example.scheduledevlv2.dto.CreateScheduleRequestDto;
import com.example.scheduledevlv2.dto.ScheduleResponseDto;
import com.example.scheduledevlv2.entity.Schedule;
import com.example.scheduledevlv2.entity.User;
import com.example.scheduledevlv2.repository.ScheduleRepository;
import com.example.scheduledevlv2.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 저장 (Schedule 객체를 받아 저장)
    @Transactional
    public ScheduleResponseDto save(String title, String contents, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Schedule schedule = new Schedule(title, contents);
        schedule.setUser(findUser); // User 객체 설정
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser().getId());  // User.getId()
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(schedule -> new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser().getId())) // User.getId()로 userId를 설정
                .collect(Collectors.toList());
    }

    // ID로 Schedule 조회
    public ScheduleResponseDto findScheduleByUserId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser().getId());
    }

    // 일정 수정
    public ScheduleResponseDto update(Long id, Long userId, CreateScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized to update this schedule");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());
        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(updatedSchedule.getId(), updatedSchedule.getTitle(), updatedSchedule.getContents(), updatedSchedule.getUser().getId());
    }

    // 일정 삭제
    public void delete(Long id, Long userId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized to delete this schedule");
        }

        scheduleRepository.delete(schedule);
    }
}
