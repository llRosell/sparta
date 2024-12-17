package com.example.scheduledevlv1.service;

import com.example.scheduledevlv1.dto.ScheduleResponseDto;
import com.example.scheduledevlv1.entity.Schedule;
import com.example.scheduledevlv1.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;  // 이 부분을 추가해야 합니다.

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 저장 (Schedule 객체를 받아 저장)
    public ScheduleResponseDto save(Schedule schedule) {
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getUsername(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto) // Schedule을 ScheduleResponseDto로 변환
                .toList();
    }

    // ID로 Schedule 조회
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = findScheduleById(id);  // Schedule 엔티티 반환
        return ScheduleResponseDto.toDto(schedule);  // Schedule 엔티티를 DTO로 변환
    }

    // ID로 Schedule 엔티티 반환
    public Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found with id " + id));
    }

    // 일정 수정 (Schedule 객체를 받아 업데이트)
    public ScheduleResponseDto update(Schedule schedule) {
        Schedule updatedSchedule = scheduleRepository.save(schedule);  // 수정된 Schedule 객체를 저장
        return ScheduleResponseDto.toDto(updatedSchedule);  // 저장 후 DTO로 반환
    }

    // 일정 삭제
    public void delete(Long id) {
        Schedule schedule = findScheduleById(id);  // ID로 Schedule 찾기
        scheduleRepository.delete(schedule);  // Schedule 삭제
    }
}


