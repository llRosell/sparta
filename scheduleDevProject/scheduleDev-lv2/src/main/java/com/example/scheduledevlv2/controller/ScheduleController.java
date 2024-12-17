package com.example.scheduledevlv2.controller;

import com.example.scheduledevlv2.dto.CreateScheduleRequestDto;
import com.example.scheduledevlv2.dto.ScheduleResponseDto;
import com.example.scheduledevlv2.service.ScheduleService;
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
    public ResponseEntity<ScheduleResponseDto> save(
            @RequestParam("userId") Long userId,
            @RequestBody CreateScheduleRequestDto dto
    ) {
        // Schedule 엔티티 생성
        ScheduleResponseDto response = scheduleService.save(dto.getTitle(), dto.getContents(), userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 반환
    }

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    // 특정 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleByUserId(@PathVariable("id") Long id) {
        ScheduleResponseDto schedule = scheduleService.findScheduleByUserId(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    // 특정 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @RequestBody CreateScheduleRequestDto dto) {
        try {
            ScheduleResponseDto updated = scheduleService.update(id, userId, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    // 특정 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {

        scheduleService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
