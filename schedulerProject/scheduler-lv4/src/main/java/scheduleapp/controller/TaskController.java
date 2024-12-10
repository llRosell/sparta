package scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleapp.dto.*;
import scheduleapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TaskController
 *
 * 할일(Task) 관련 요청을 처리하는 컨트롤러 클래스입니다.
 * 할일의 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/schedules")
public class TaskController {

    // TaskService: 할일과 관련된 비즈니스 로직을 처리하는 서비스
    private final TaskService taskService;

    // Logger: 요청 및 오류 로그를 기록하기 위한 로거
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * TaskController 생성자
     *
     * @param taskService TaskService 객체를 주입받아 초기화합니다.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 할일 생성 메서드
     *
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호(권한 확인용)
     * @param dto TaskRequestDto 객체로 할일 생성에 필요한 데이터를 전달받습니다.
     * @return 생성된 할일 정보를 담은 TaskResponseDto와 HTTP 상태 코드 201(CREATED)을 반환합니다.
     */
    @PostMapping("/task")
    public ResponseEntity<TaskResponseDto> saveTask(
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password,
            @RequestBody TaskRequestDto dto) {
        try {
            // 비밀번호 검증
            if (!taskService.verifyAuthorPassword(authorId, password)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
            }

            // 할일 생성 및 응답 반환
            TaskResponseDto response = taskService.saveTask(authorId, dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 반환
        } catch (RuntimeException e) {
            // 작성자 미발견 또는 기타 비즈니스 오류
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 반환
        } catch (Exception e) {
            // 일반적인 예외 처리
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 반환
        }
    }

    /**
     * 할일 단일 조회 메서드
     *
     * @param id 조회할 할일의 ID
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호(권한 확인용)
     * @return 조회된 할일 정보를 담은 TaskResponseDto와 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> getTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password) {
        try {
            TaskResponseDto task = taskService.getTask(id, authorId, password);
            return new ResponseEntity<>(task, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    /**
     * 할일 전체 조회 메서드
     *
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호(권한 확인용)
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 작성자의 모든 할일 목록과 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @GetMapping("/task")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        logger.info("GET /tasks 요청 - authorId: {}, page: {}, size: {}", authorId, page, size);

        try {
            List<TaskResponseDto> tasks = taskService.getAllTasks(authorId, password, page, size);
            logger.info("할일 조회 성공 - authorId: {}, 할일 수: {}", authorId, tasks.size());
            return new ResponseEntity<>(tasks, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            logger.error("비밀번호 검증 실패 - authorId: {}, 에러 메시지: {}", authorId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    /**
     * 할일 수정 메서드
     *
     * @param id 수정할 할일의 ID
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호(권한 확인용)
     * @param dto TaskRequestDto 객체로 수정할 데이터를 전달받습니다.
     * @return 수정된 할일 정보를 담은 TaskResponseDto와 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestBody TaskRequestDto dto,
            @RequestParam("password") String password) {
        try {
            TaskResponseDto updatedTask = taskService.updateTask(id, authorId, password, dto);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK); // 200 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }

    /**
     * 할일 삭제 메서드
     *
     * @param id 삭제할 할일의 ID
     * @param authorId 작성자의 ID
     * @param password 작성자의 비밀번호(권한 확인용)
     * @return HTTP 상태 코드 204(No Content)를 반환합니다.
     */
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id,
            @RequestParam("authorId") Long authorId,
            @RequestParam("password") String password) {
        try {
            taskService.deleteTask(id, password);
            return ResponseEntity.noContent().build(); // 204 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 반환
        }
    }
}
