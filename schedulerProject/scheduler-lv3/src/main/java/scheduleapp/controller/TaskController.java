package scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleapp.dto.*;
import scheduleapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class); // Logger 초기화

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 할일 생성
    @PostMapping("/task")
    public ResponseEntity<TaskResponseDto> saveTask(
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password,
            @RequestBody TaskRequestDto dto) {
        try {
            // 할 일을 저장하기 전에 비밀번호를 검증
            if (!taskService.verifyAuthorPassword(authorId, password)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 비밀번호가 일치하지 않으면 401 반환
            }

            // 할 일을 저장하고 응답 DTO를 반환
            TaskResponseDto response = taskService.saveTask(authorId, dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 상태 코드 201 반환
        } catch (RuntimeException e) {
            // 작성자를 찾을 수 없거나 다른 예외가 발생한 경우
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 상태 코드 404 반환
        } catch (Exception e) {
            // 일반적인 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 상태 코드 500 반환
        }
    }

    // 할일 조회
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> getTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password
    ) {
        try {
            TaskResponseDto task = taskService.getTask(id, authorId, password);
            return new ResponseEntity<>(task, HttpStatus.OK); // 상태 코드 200 반환
        } catch (RuntimeException e) {
            // 비밀번호 검증 실패 또는 데이터 미발견
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 상태 코드 401 반환
        }
    }

    // 할일 전체 조회
    @GetMapping("/task")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password
    ) {
        logger.info("GET /tasks 요청 - authorId: {}, password: {}", authorId, password); // 요청 정보 로그

        try {
            List<TaskResponseDto> tasks = taskService.getAllTasks(authorId, password);
            logger.info("할일 조회 성공 - authorId: {}, 할일 수: {}", authorId, tasks.size()); // 성공 로그
            return new ResponseEntity<>(tasks, HttpStatus.OK); // 상태 코드 200 반환
        } catch (RuntimeException e) {
            logger.error("비밀번호 검증 실패 - authorId: {}, 에러 메시지: {}", authorId, e.getMessage()); // 오류 로그
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 상태 코드 401 반환
        }
    }
    // 할일 수정
    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestBody TaskRequestDto dto,
            @RequestParam("password") String password
    ) {
        try {
            TaskResponseDto updatedTask = taskService.updateTask(id, authorId, password, dto);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK); // 상태 코드 200 반환
        } catch (RuntimeException e) {
            // 비밀번호 검증 실패 또는 데이터 미발견
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 상태 코드 401 반환
        }
    }

    // 할일 삭제
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password
    ) {
        try {
            taskService.deleteTask(id, password);
            return ResponseEntity.noContent().build(); // 상태 코드 204 반환
        } catch (RuntimeException e) {
            // 비밀번호 검증 실패 또는 데이터 미발견
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 상태 코드 401 반환
        }
    }
}
