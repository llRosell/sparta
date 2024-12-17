package com.example.scheduledevlv3.service;

import com.example.scheduledevlv3.dto.ScheduleRequestDto;
import com.example.scheduledevlv3.dto.ScheduleResponseDto;
import com.example.scheduledevlv3.entity.Schedule;
import com.example.scheduledevlv3.entity.User;
import com.example.scheduledevlv3.repository.ScheduleRepository;
import com.example.scheduledevlv3.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// ScheduleService.java 수정

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 저장
    @Transactional
    public ScheduleResponseDto save(Long userId, ScheduleRequestDto dto) {
        // 작성자의 비밀번호를 검증합니다.
        verifyUserPassword(userId, dto.getPassword());

        Schedule schedule = new Schedule();
        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        // User 객체를 참조하는 부분
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        schedule.setUser(user);  // User 객체를 세팅

        schedule = scheduleRepository.save(schedule); // 저장 후 응답 반환
        return new ScheduleResponseDto(schedule);
    }

    // 특정 일정 조회
    public ScheduleResponseDto findScheduleByUserId(Long id, Long userId, String password) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        // 비밀번호 검증 (User 객체에서 비밀번호 가져오기)
        if (!schedule.getUser().getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        return new ScheduleResponseDto(schedule);
    }

    // 모든 일정 조회
    public List<ScheduleResponseDto> getAllSchedules(Long userId, String password) {
        verifyUserPassword(userId, password);

        // 해당 userId에 속한 모든 일정을 가져옴
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);
        return schedules.stream()
                .map(ScheduleResponseDto::new)  // Schedule 객체를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 일정 업데이트
    @Transactional
    public ScheduleResponseDto update(Long id, Long userId, String password, ScheduleRequestDto dto) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setContents(dto.getContents());

        schedule = scheduleRepository.save(schedule); // 업데이트 반영
        return new ScheduleResponseDto(schedule);
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id, String password) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Schedule schedule = optionalSchedule.get();
        if (!schedule.getUser().getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(schedule);
    }

    // 비밀번호 검증
    public boolean verifyUserPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getPassword().equals(password);  // User에서 비밀번호를 가져와 비교
    }
}
