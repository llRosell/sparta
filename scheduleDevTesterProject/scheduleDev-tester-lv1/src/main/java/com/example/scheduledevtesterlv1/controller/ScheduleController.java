package com.example.scheduledevtesterlv1.controller;

import com.example.scheduledevtesterlv1.dto.ScheduleRequestDto;
import com.example.scheduledevtesterlv1.dto.ScheduleResponseDto;
import com.example.scheduledevtesterlv1.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
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
     * 사용자 ID와 일정 정보를 받아 일정을 생성하고, 생성된 일정 정보를 반환합니다.
     * @param userId 로그인된 사용자의 ID
     * @param dto 생성할 일정의 정보
     * @return 일정 생성 결과
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @RequestParam("userId") Long userId,  // URL 파라미터로 userId 받기
            @Valid  @RequestBody ScheduleRequestDto dto) { // 요청 본문에서 할 일 데이터 받기
        try {
            // 로그인되지 않은 경우 401 반환
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // 일정 저장 요청 처리
            ScheduleResponseDto response = scheduleService.save(userId, dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 반환
        } catch (Exception e) {
            // 오류 발생 시 401 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 전체 일정 조회 API
     * 사용자의 모든 일정을 조회하여 반환합니다.
     * @param userId 로그인된 사용자의 ID
     * @param request HTTP 요청 객체
     * @return 사용자의 일정 목록
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam("userId") Long userId,
            HttpServletRequest request) {
        try {
            // 로그인되지 않은 경우 401 반환
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // 사용자 일정 목록 조회
            List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(userId);
            return new ResponseEntity<>(schedules, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            // 예외 발생 시 401 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 특정 일정 조회 API
     * 사용자 ID와 일정 ID를 통해 특정 일정을 조회하여 반환합니다.
     * @param id 조회할 일정의 ID
     * @param userId 로그인된 사용자의 ID
     * @param request HTTP 요청 객체
     * @return 특정 일정 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            HttpServletRequest request) {
        try {
            // 로그인되지 않은 경우 401 반환
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // 특정 일정 조회
            ScheduleResponseDto schedule = scheduleService.findScheduleByUserId(id, userId);
            return new ResponseEntity<>(schedule, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            // 예외 발생 시 401 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 특정 일정 수정 API
     * 일정 ID와 사용자 ID를 통해 특정 일정을 수정하고 수정된 일정을 반환합니다.
     * @param id 수정할 일정의 ID
     * @param userId 로그인된 사용자의 ID
     * @param dto 수정할 일정 정보
     * @return 수정된 일정 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @Valid @RequestBody ScheduleRequestDto dto) {
        try {
            // 일정 수정 처리
            ScheduleResponseDto updatedSchedule = scheduleService.update(id, userId, dto);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            // 수정 중 예외 발생 시 401 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 특정 일정 삭제 API
     * 일정 ID와 사용자 ID를 통해 특정 일정을 삭제합니다.
     * @param id 삭제할 일정의 ID
     * @param userId 로그인된 사용자의 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {
        try {
            // 일정 삭제 처리
            scheduleService.delete(id, userId);
            return new ResponseEntity<>("일정이 삭제되었습니다.", HttpStatus.OK); // 성공 메시지 반환
        } catch (Exception e) {
            // 예외 발생 시 401 반환
            return new ResponseEntity<>("일정 삭제 중 문제가 발생했습니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 일정 페이징 조회 API
     * 사용자의 일정을 페이징 처리하여 조회하고 반환합니다.
     * @param userId 로그인된 사용자의 ID
     * @param page 조회할 페이지 번호
     * @param size 조회할 페이지 크기
     * @return 일정 목록 (페이징 처리)
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<ScheduleResponseDto>> getSchedulesWithPaging(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,  // 기본값은 첫 페이지
            @RequestParam(value = "size", defaultValue = "10") int size) {  // 기본값은 10개 항목

        try {
            // 로그인되지 않은 경우 401 반환
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // 페이징 정보 기반으로 일정 조회
            Pageable pageable = PageRequest.of(page, size);
            Page<ScheduleResponseDto> schedulesPage = scheduleService.getSchedulesWithPaging(userId, pageable);
            return new ResponseEntity<>(schedulesPage, HttpStatus.OK); // 200 반환
        } catch (Exception e) {
            // 예외 발생 시 401 반환
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
