package scheduleapp.service;

import scheduleapp.dto.TaskRequestDto;
import scheduleapp.dto.TaskResponseDto;

import java.util.List;

/**
 * TaskService 인터페이스
 *
 * 일정(Task) 관련 비즈니스 로직을 정의하는 인터페이스입니다.
 * 일정의 저장, 조회, 수정 및 삭제 기능을 제공합니다.
 */
public interface TaskService {

    TaskResponseDto saveTask(Long authorId, TaskRequestDto dto); // 할 일 저장
    TaskResponseDto getTask(Long id, Long authorId, String password); // 할 일 단건 조회
    List<TaskResponseDto> getAllTasks(Long authorId, String password, int page, int size); // 할 일 전체 조회
    TaskResponseDto updateTask(Long id, Long authorId, String password, TaskRequestDto dto); // 할 일 수정
    void deleteTask(Long id, String password);
    boolean verifyAuthorPassword(Long authorId, String password); // 할 일 삭제
}
