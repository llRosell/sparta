package com.example.scheduledevlv3.controller;

import com.example.scheduledevlv3.dto.ScheduleRequestDto;
import com.example.scheduledevlv3.dto.ScheduleResponseDto;
import com.example.scheduledevlv3.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @RequestParam("userId") Long userId,
            @RequestParam("password") String password,
            @RequestBody ScheduleRequestDto dto) {
        try {
            // 비밀번호 검증
            if (!scheduleService.verifyUserPassword(userId, password)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
            }

            // 할일 생성 및 응답 반환
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
            @RequestParam("password") String password
    ) {
        try {
            List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(userId, password);
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
            @RequestParam("password") String password) {
        try {
            ScheduleResponseDto schedule = scheduleService.findScheduleByUserId(id, userId, password);
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
            @RequestBody ScheduleRequestDto dto,
            @RequestParam("password") String password) {
        try {
            ScheduleResponseDto updatedSchedule = scheduleService.update(id, userId, password, dto);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    // 특정 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @RequestParam("password") String password) {
        try {
            scheduleService.delete(id, password);
            return ResponseEntity.noContent().build(); // 204 반환
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }
}