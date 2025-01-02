package com.example.scheduledevtesterlv2.service;

import com.example.scheduledevtesterlv2.dto.ScheduleRequestDto;
import com.example.scheduledevtesterlv2.dto.ScheduleResponseDto;
import com.example.scheduledevtesterlv2.entity.Schedule;
import com.example.scheduledevtesterlv2.entity.User;
import com.example.scheduledevtesterlv2.repository.ScheduleRepository;
import com.example.scheduledevtesterlv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 일정 저장
     */
    public ScheduleResponseDto save(Long userId, ScheduleRequestDto dto) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자를 찾을 수 없습니다."));

        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());
        schedule.setUser(user);

        schedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 특정 일정 조회
     */
    public ScheduleResponseDto findScheduleByUserId(Long id, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("이 일정은 해당 사용자에게 속하지 않습니다.");
        }

        return new ScheduleResponseDto(schedule);
    }

    /**
     * 모든 일정 조회
     */
    public List<ScheduleResponseDto> getAllSchedules(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 일정 수정
     */
    public ScheduleResponseDto update(Long id, Long userId, ScheduleRequestDto dto) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("이 일정은 해당 사용자에게 속하지 않습니다.");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());
        schedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 삭제
     */
    public void delete(Long id, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("이 일정은 해당 사용자에게 속하지 않습니다.");
        }

        scheduleRepository.delete(schedule);
    }

    /**
     * 일정 페이징 조회
     */
    public Page<ScheduleResponseDto> getSchedulesWithPaging(Long userId, Pageable pageable) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Page<Schedule> schedulePage = scheduleRepository.findByUserIdOrderByModifiedAtDesc(userId, pageable);
        return schedulePage.map(ScheduleResponseDto::new);
    }
}
