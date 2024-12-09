package scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleapp.dto.ScheduleRequestDto;
import scheduleapp.dto.ScheduleResponseDto;
import scheduleapp.entity.Schedule;
import scheduleapp.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto) {
        ScheduleResponseDto response = scheduleService.saveSchedule(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 상태 코드 201로 반환
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK); // 상태 코드 200으로 반환
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable("id") Long id) {
        ScheduleResponseDto schedule = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK); // 상태 코드 200으로 반환
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable("id") Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        ScheduleResponseDto updatedSchedule = scheduleService.updateSchedule(id, dto);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("id") Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        scheduleService.deleteSchedule(id, dto);
        return ResponseEntity.noContent().build(); // 상태 코드 204 No Content 반환
    }
}
