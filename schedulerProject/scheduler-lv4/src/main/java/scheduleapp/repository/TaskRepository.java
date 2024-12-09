package scheduleapp.repository;

import scheduleapp.dto.TaskRequestDto;
import scheduleapp.dto.TaskResponseDto;
import scheduleapp.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    // 일정 저장 (작성자 ID, 비밀번호 검증)
    TaskResponseDto saveTask(Task task);

    // 일정 단건 조회 (작성자 ID, 비밀번호 검증)
    Optional<Task> getTask(Long id);

    // 모든 일정 조회 (작성자 ID, 비밀번호 검증)
    List<Task> getAllTasks(Long authorId, int page, int size);

    // 일정 수정 (작성자 ID, 비밀번호 검증)
    int updateTask(Task task);

    // 일정 삭제 (작성자 ID, 비밀번호 검증)
    void deleteTask(Long id);
}
