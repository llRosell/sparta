package scheduleapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import scheduleapp.dto.TaskRequestDto;
import scheduleapp.dto.TaskResponseDto;
import scheduleapp.entity.Task;
import scheduleapp.repository.AuthorRepository;
import scheduleapp.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthorRepository authorRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AuthorRepository authorRepository) {
        this.taskRepository = taskRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public TaskResponseDto saveTask(Long authorId, TaskRequestDto dto) {
        verifyAuthorPassword(dto.getAuthorId(), dto.getPassword());

        Task task = new Task();
        task.setTodo(dto.getTodo());
        task.setAuthorId(dto.getAuthorId());
        task.setPassword(dto.getPassword()); // 작성자의 비밀번호 설정

        return taskRepository.saveTask(task);
    }

    @Override
    public TaskResponseDto getTask(Long id, Long authorId, String password) {
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get();
        verifyAuthorPassword(task.getAuthorId(), password);

        return new TaskResponseDto(
                task.getId(),
                task.getTodo(),
                task.getAuthorId()
        );
    }

    @Override
    public List<TaskResponseDto> getAllTasks(Long authorId, String password, int page, int size) {
        verifyAuthorPassword(authorId, password);

        List<Task> tasks = taskRepository.getAllTasks(authorId, page, size);

        return tasks.stream()
                .filter(task -> task.getAuthorId() != null && task.getAuthorId().equals(authorId)) // 해당 작성자의 작업만 반환
                .map(task -> new TaskResponseDto(
                        task.getId(),
                        task.getTodo(),
                        task.getAuthorId()
                )) // DTO로 변환
                .collect(Collectors.toList()); // List로 변환
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, Long authorId, String password, TaskRequestDto dto) {
        // ID로 작업을 찾기
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get();

        // 비밀번호 검증
        verifyAuthorPassword(task.getAuthorId(), password);

        // 할 일 업데이트
        task.setTodo(dto.getTodo()); // dto에서 todo 값을 가져와서 설정

        // 데이터베이스에 업데이트 반영
        taskRepository.updateTask(task);

        // 업데이트된 TaskResponseDto 반환
        return new TaskResponseDto(
                task.getId(),
                task.getTodo(),
                task.getAuthorId()
        );
    }

    @Override
    @Transactional
    public void deleteTask(Long id, String password) {
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get();
        verifyAuthorPassword(task.getAuthorId(), password);

        taskRepository.deleteTask(id);
    }

    @Override
    public boolean verifyAuthorPassword(Long authorId, String password) {
        String storedPassword = authorRepository.findPasswordById(authorId);
        return storedPassword != null && storedPassword.equals(password);
    }
}
