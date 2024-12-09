package scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scheduleapp.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.name = schedule.getName();
        this.created = schedule.getCreated();
        this.updated = schedule.getUpdated();
    }
}
