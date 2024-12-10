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

/**
 * TaskServiceImpl 클래스
 *
 * 일정(Task) 관련 비즈니스 로직을 구현한 서비스 클래스입니다.
 * 일정의 저장, 조회, 수정 및 삭제 기능을 제공합니다.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository; // 일정 저장소
    private final AuthorRepository authorRepository; // 작성자 저장소

    public TaskServiceImpl(TaskRepository taskRepository, AuthorRepository authorRepository) {
        this.taskRepository = taskRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * 새로운 일정을 저장합니다.
     *
     * @param authorId 작성자의 ID
     * @param dto 일정 요청 데이터 전송 객체
     * @return 저장된 일정에 대한 응답 데이터 전송 객체
     */
    @Override
    @Transactional
    public TaskResponseDto saveTask(Long authorId, TaskRequestDto dto) {
        // 작성자의 비밀번호를 검증합니다.
        verifyAuthorPassword(dto.getAuthorId(), dto.getPassword());

        // 새 Task 객체 생성
        Task task = new Task();
        task.setTodo(dto.getTodo()); // 요청 데이터에서 할 일 설정
        task.setAuthorId(dto.getAuthorId()); // 작성자 ID 설정
        task.setPassword(dto.getPassword()); // 작성자의 비밀번호 설정

        return taskRepository.saveTask(task); // 저장 후 응답 반환
    }

    /**
     * 특정 일정의 세부 정보를 조회합니다.
     *
     * @param id 일정 ID
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호
     * @return 조회된 일정에 대한 응답 데이터 전송 객체
     */
    @Override
    public TaskResponseDto getTask(Long id, Long authorId, String password) {
        // ID로 Task 조회
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get(); // Task 객체 가져오기
        // 작성자의 비밀번호 검증
        verifyAuthorPassword(task.getAuthorId(), password);

        // TaskResponseDto 객체 생성하여 반환
        return new TaskResponseDto(
                task.getId(),
                task.getTodo(),
                task.getAuthorId()
        );
    }

    /**
     * 작성자의 모든 일정을 조회합니다.
     *
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호
     * @param page 요청할 페이지 번호
     * @param size 한 페이지에 표시할 일정 수
     * @return 조회된 일정 목록
     */
    @Override
    public List<TaskResponseDto> getAllTasks(Long authorId, String password, int page, int size) {
        // 작성자의 비밀번호 검증
        verifyAuthorPassword(authorId, password);

        // 작성자의 모든 일정 조회
        List<Task> tasks = taskRepository.getAllTasks(authorId, page, size);

        // Task 객체를 TaskResponseDto로 변환하여 반환
        return tasks.stream()
                .filter(task -> task.getAuthorId() != null && task.getAuthorId().equals(authorId)) // 해당 작성자의 작업만 반환
                .map(task -> new TaskResponseDto(
                        task.getId(),
                        task.getTodo(),
                        task.getAuthorId()
                )) // DTO로 변환
                .collect(Collectors.toList()); // List로 변환
    }

    /**
     * 기존 일정을 수정합니다.
     *
     * @param id 일정 ID
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호
     * @param dto 수정할 일정 요청 데이터 전송 객체
     * @return 수정된 일정에 대한 응답 데이터 전송 객체
     */
    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, Long authorId, String password, TaskRequestDto dto) {
        // ID로 작업을 찾기
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get(); // Task 객체 가져오기

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

    /**
     * 특정 일정을 삭제합니다.
     *
     * @param id 삭제할 일정의 ID
     * @param password 작성자의 비밀번호
     */
    @Override
    @Transactional
    public void deleteTask(Long id, String password) {
        // ID로 작업을 찾기
        Optional<Task> optionalTask = taskRepository.getTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID가 " + id + "인 할 일을 찾을 수 없습니다.");
        }

        Task task = optionalTask.get(); // Task 객체 가져오기
        // 비밀번호 검증
        verifyAuthorPassword(task.getAuthorId(), password);

        // Task 삭제
        taskRepository.deleteTask(id);
    }

    /**
     * 작성자의 비밀번호를 검증합니다.
     *
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호
     * @return 비밀번호가 일치하면 true, 아니면 false
     */
    @Override
    public boolean verifyAuthorPassword(Long authorId, String password) {
        String storedPassword = authorRepository.findPasswordById(authorId); // 작성자의 비밀번호를 데이터베이스에서 가져옴
        return storedPassword != null && storedPassword.equals(password); // 비밀번호가 일치하는지 확인
    }
}
