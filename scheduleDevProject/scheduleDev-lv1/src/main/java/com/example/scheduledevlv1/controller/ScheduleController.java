package com.example.scheduledevlv1.controller;

import com.example.scheduledevlv1.dto.CreateScheduleRequestDto;
import com.example.scheduledevlv1.dto.ScheduleResponseDto;
import com.example.scheduledevlv1.entity.Schedule;
import com.example.scheduledevlv1.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {
        // Schedule 엔티티 생성
        Schedule schedule = new Schedule(requestDto.getUsername(), requestDto.getTitle(), requestDto.getContents());
        ScheduleResponseDto scheduleResponseDto = scheduleService.save(schedule);  // Schedule 객체를 전달
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    // 특정 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable("id") Long id) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    // 특정 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody CreateScheduleRequestDto dto
    ) {
        // 1. ID로 Schedule 엔티티를 찾음
        Schedule schedule = scheduleService.findScheduleById(id);  // Schedule 엔티티 반환

        // 2. Schedule 엔티티에 dto 값 적용하여 수정
        schedule.setUsername(dto.getUsername());
        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        // 3. 수정된 Schedule 엔티티를 DB에 저장
        ScheduleResponseDto updatedSchedule = scheduleService.update(schedule);  // 수정된 Schedule을 저장하고 반환

        // 4. 업데이트된 ScheduleResponseDto를 반환
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

