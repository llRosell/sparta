package scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scheduleapp.entity.Task;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long id;
    private String todo;
    private Long authorId;

    // Entity를 DTO로 변환하는 생성자
    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.todo = task.getTodo();
        this.authorId = task.getAuthorId();
    }
}
