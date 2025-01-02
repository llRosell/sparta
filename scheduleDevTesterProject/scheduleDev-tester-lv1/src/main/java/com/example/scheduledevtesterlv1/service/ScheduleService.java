package com.example.scheduledevtesterlv1.service;

import com.example.scheduledevtesterlv1.dto.ScheduleRequestDto;
import com.example.scheduledevtesterlv1.dto.ScheduleResponseDto;
import com.example.scheduledevtesterlv1.entity.Schedule;
import com.example.scheduledevtesterlv1.entity.User;
import com.example.scheduledevtesterlv1.repository.ScheduleRepository;
import com.example.scheduledevtesterlv1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;  // Schedule 레포지토리 의존성 주입
    private final UserRepository userRepository;  // User 레포지토리 의존성 주입

    /**
     * 새로운 일정 저장
     * @param userId 일정에 대한 사용자 ID
     * @param dto 일정에 대한 요청 DTO
     * @return 저장된 일정 정보 응답 DTO
     */
    public ScheduleResponseDto save(Long userId, ScheduleRequestDto dto) {
        // 해당 userId에 해당하는 User를 찾음
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 새로운 Schedule 객체 생성
        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());  // 일정 제목 설정
        schedule.setContents(dto.getContents());  // 일정 내용 설정
        schedule.setUser(user);  // 사용자 정보 설정

        // 일정 저장 후 응답 DTO 반환
        schedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 특정 일정 조회
     * @param id 조회할 일정 ID
     * @param userId 조회할 사용자 ID
     * @return 조회된 일정의 응답 DTO
     */
    public ScheduleResponseDto findScheduleByUserId(Long id, Long userId) {
        // 일정 조회
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            // 일정이 존재하지 않으면 NOT_FOUND 예외 발생
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(userId)) {
            // 사용자가 다른 사람이라면 FORBIDDEN 예외 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        // 조회된 일정 정보 반환
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 사용자가 작성한 모든 일정 조회
     * @param userId 조회할 사용자 ID
     * @return 사용자가 작성한 모든 일정 리스트 (DTO)
     */
    public List<ScheduleResponseDto> getAllSchedules(Long userId) {
        // 해당 userId에 속한 모든 일정을 조회
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)  // 각 Schedule을 DTO로 변환
                .collect(Collectors.toList());  // 리스트로 반환
    }

    /**
     * 일정 수정
     * @param id 수정할 일정 ID
     * @param userId 수정할 사용자 ID
     * @param dto 수정할 일정 내용 (DTO)
     * @return 수정된 일정 정보 응답 DTO
     */
    public ScheduleResponseDto update(Long id, Long userId, ScheduleRequestDto dto) {
        // 일정 조회
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            // 일정이 존재하지 않으면 NOT_FOUND 예외 발생
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(userId)) {
            // 사용자가 다른 사람이라면 FORBIDDEN 예외 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        // 일정 내용 수정
        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        // 수정된 일정 저장 후 응답 DTO 반환
        schedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 삭제
     * @param id 삭제할 일정 ID
     * @param userId 삭제할 사용자 ID
     */
    public void delete(Long id, Long userId) {
        // 일정 조회
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            // 일정이 존재하지 않으면 NOT_FOUND 예외 발생
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getId().equals(userId)) {
            // 사용자가 다른 사람이라면 FORBIDDEN 예외 발생
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이 일정은 다른 사용자에 속해 있습니다.");
        }

        // 일정 삭제
        scheduleRepository.delete(schedule);
    }

    /**
     * 일정 페이징 조회
     * @param userId 페이징 조회할 사용자 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기 등)
     * @return 일정 페이징 응답 DTO
     */
    public Page<ScheduleResponseDto> getSchedulesWithPaging(Long userId, Pageable pageable) {
        // 페이징 및 수정일 기준 내림차순으로 일정 조회
        Page<Schedule> schedulePage = scheduleRepository.findByUserIdOrderByModifiedAtDesc(userId, pageable);

        // 조회된 일정들을 DTO로 변환하여 반환
        return schedulePage.map(schedule -> new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getComments().size(),  // 댓글 개수
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                schedule.getUser().getId()
        ));
    }
}