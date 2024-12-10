package scheduleapp.repository;

import scheduleapp.dto.TaskResponseDto;
import scheduleapp.entity.Task;

import java.util.List;
import java.util.Optional;

/**
 * TaskRepository 인터페이스
 *
 * 작업(Task) 데이터에 대한 CRUD(Create, Read, Update, Delete) 연산을 정의합니다.
 * 작성자의 ID와 비밀번호 검증을 포함하여 작업을 처리합니다.
 */
public interface TaskRepository {

    TaskResponseDto saveTask(Task task); // 할 일 저장
    Optional<Task> getTask(Long id); // 할 일 단건 조회
    List<Task> getAllTasks(Long authorId, int page, int size); // 할 일 전체 조회
    int updateTask(Task task); // 할 일 수정
    void deleteTask(Long id); // 할 일 삭제
}
