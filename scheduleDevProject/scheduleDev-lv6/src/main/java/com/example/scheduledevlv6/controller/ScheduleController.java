package com.example.scheduledevlv6.controller;

import com.example.scheduledevlv6.dto.ScheduleRequestDto;
import com.example.scheduledevlv6.dto.ScheduleResponseDto;
import com.example.scheduledevlv6.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
            @RequestParam("userId") Long userId,  // URL 파라미터로 userId 받기
            @Valid  @RequestBody ScheduleRequestDto dto) { // 요청 본문에서 할 일 데이터 받기
        try {
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 로그인되지 않은 경우
            }

            // Schedule 객체 생성 및 서비스 호출
            ScheduleResponseDto response = scheduleService.save(userId, dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 반환
        }
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam("userId") Long userId,
            HttpServletRequest request) {
        try {
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 로그인되지 않은 경우
            }

            List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(userId);
            return new ResponseEntity<>(schedules, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    // 특정 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            HttpServletRequest request) {
        try {
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 로그인되지 않은 경우
            }

            ScheduleResponseDto schedule = scheduleService.findScheduleByUserId(id, userId);
            return new ResponseEntity<>(schedule, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    // 특정 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @Valid @RequestBody ScheduleRequestDto dto) {
        try {
            ScheduleResponseDto updatedSchedule = scheduleService.update(id, userId, dto);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    // 특정 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {
        try {
            scheduleService.delete(id, userId);
            return ResponseEntity.noContent().build(); // 204 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }
}
