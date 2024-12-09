package scheduleapp.service;

import scheduleapp.dto.AuthorRequestDto;
import scheduleapp.dto.TaskRequestDto;
import scheduleapp.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {  // 여기에 오타 수정
    TaskResponseDto saveTask(Long authorId, TaskRequestDto dto);
    TaskResponseDto getTask(Long id, Long authorId, String password);
    List<TaskResponseDto> getAllTasks(Long authorId, String password);
    TaskResponseDto updateTask(Long id, Long authorI,String password, TaskRequestDto dto);
    void deleteTask(Long id, String password);
    boolean verifyAuthorPassword(Long authorId, String password); // 메서드
} // 인터페이스의 닫는 괄호가 포함되어 있는지 확인
