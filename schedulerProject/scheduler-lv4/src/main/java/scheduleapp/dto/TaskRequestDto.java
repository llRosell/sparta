package scheduleapp.dto;

import lombok.Getter;

@Getter
public class TaskRequestDto {
    private String todo;
    private Long authorId;// 할 일 제목
    private String password;
}
