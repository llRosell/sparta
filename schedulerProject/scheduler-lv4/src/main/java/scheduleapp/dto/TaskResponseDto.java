package scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import scheduleapp.entity.Task;

/**
 * TaskResponseDto
 *
 * 할 일 관련 응답 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 */
@Getter
@AllArgsConstructor
public class TaskResponseDto {

    private Long id; // 할 일 ID
    private String todo; // 할 일 내용
    private Long authorId; // 작성자 ID

    /**
     * Task 엔티티를 TaskResponseDto로 변환하는 생성자.
     *
     * @param task Task 엔티티 객체
     *             - Entity의 데이터를 기반으로 DTO 필드에 값을 설정합니다.
     */
    public TaskResponseDto(Task task) {
        this.id = task.getId();        // 할 일 ID 설정
        this.todo = task.getTodo();    // 할 일 내용 설정
        this.authorId = task.getAuthorId(); // 작성자 ID 설정
    }
}
