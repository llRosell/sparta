package com.example.scheduledevtesterlv2.controller;

import com.example.scheduledevtesterlv2.dto.ScheduleRequestDto;
import com.example.scheduledevtesterlv2.dto.ScheduleResponseDto;
import com.example.scheduledevtesterlv2.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성 API
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @RequestParam("userId") Long userId,
            @Valid @RequestBody ScheduleRequestDto dto) {
        ScheduleResponseDto response = scheduleService.save(userId, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 모든 일정 조회 API
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam("userId") Long userId) {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(userId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    /**
     * 특정 일정 조회 API
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {
        ScheduleResponseDto schedule = scheduleService.findScheduleByUserId(id, userId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    /**
     * 일정 수정 API
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @Valid @RequestBody ScheduleRequestDto dto) {
        ScheduleResponseDto updatedSchedule = scheduleService.update(id, userId, dto);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {
        scheduleService.delete(id, userId);
        return new ResponseEntity<>("일정이 삭제되었습니다.", HttpStatus.OK);
    }

    /**
     * 일정 페이징 조회 API
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<ScheduleResponseDto>> getSchedulesWithPaging(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleResponseDto> schedulesPage = scheduleService.getSchedulesWithPaging(userId, pageable);
        return new ResponseEntity<>(schedulesPage, HttpStatus.OK);
    }
}
