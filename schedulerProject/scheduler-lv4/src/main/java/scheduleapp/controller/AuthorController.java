package scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleapp.dto.*;
import scheduleapp.service.AuthorService;

/**
 * AuthorController
 *
 * 작성자 관련 요청을 처리하는 컨트롤러 클래스입니다.
 * 작성자의 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/schedules")
public class AuthorController {

    // AuthorService: 작성자와 관련된 비즈니스 로직을 처리하는 서비스
    private final AuthorService authorService;

    /**
     * AuthorController 생성자
     *
     * @param authorService AuthorService 객체를 주입받아 초기화합니다.
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * 작성자 생성 메서드
     *
     * @param dto AuthorRequestDto 객체로 작성자 생성에 필요한 데이터를 전달받습니다.
     * @return 생성된 작성자의 정보를 담은 AuthorResponseDto와 HTTP 상태 코드 201(CREATED)을 반환합니다.
     */
    @PostMapping("/author")
    public ResponseEntity<AuthorResponseDto> saveAuthor(
            @RequestBody AuthorRequestDto dto) {
        AuthorResponseDto response = authorService.saveAuthor(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 작성자 조회 메서드
     *
     * @param id 작성자의 고유 식별자(ID)
     * @param password 작성자의 비밀번호(권한 확인용)
     * @return 조회된 작성자의 정보를 담은 AuthorResponseDto와 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(
            @PathVariable("id") Long id,
            @RequestParam(name = "password") String password
    ) {
        AuthorResponseDto author = authorService.getAuthor(id, password);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * 작성자 수정 메서드
     *
     * @param id 수정할 작성자의 고유 식별자(ID)
     * @param dto AuthorRequestDto 객체로 수정할 데이터를 전달받습니다.
     * @param password 작성자의 비밀번호(권한 확인용)
     * @return 수정된 작성자의 정보를 담은 AuthorResponseDto와 HTTP 상태 코드 200(OK)을 반환합니다.
     */
    @PutMapping("/author/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorRequestDto dto,
            @RequestParam("password") String password
    ) {
        AuthorResponseDto updatedAuthor = authorService.updateAuthor(id, dto, password);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    /**
     * 작성자 삭제 메서드
     *
     * @param id 삭제할 작성자의 고유 식별자(ID)
     * @param password 작성자의 비밀번호(권한 확인용)
     * @return HTTP 상태 코드 204(No Content)를 반환합니다.
     */
    @DeleteMapping("/author/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("id") Long id,
            @RequestParam("password") String password
    ) {
        authorService.deleteAuthor(id, password);
        return ResponseEntity.noContent().build();
    }
}
