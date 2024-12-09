package scheduleapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scheduleapp.dto.*;
import scheduleapp.service.AuthorService;

@RestController
@RequestMapping("/api/schedules")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // 작성자 생성
    @PostMapping("/author")
    public ResponseEntity<AuthorResponseDto> saveAuthor(
            @RequestBody AuthorRequestDto dto) {
        AuthorResponseDto response = authorService.saveAuthor(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 상태 코드 201로 반환
    }

    // 작성자 조회
    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(
            @PathVariable("id") Long id,
            @RequestParam(name = "password") String password
    ) {
        // 비밀번호를 포함하여 authorService.getAuthor 메서드 호출
        AuthorResponseDto author = authorService.getAuthor(id, password);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }


    // 작성자 수정
    @PutMapping("/author/{id}") // 경로 수정
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorRequestDto dto,
            @RequestParam("password") String password
    ) {
        AuthorResponseDto updatedAuthor = authorService.updateAuthor(id, dto,  password); // 수정
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK); // 상태 코드 200 반환
    }

    // 작성자 삭제
    @DeleteMapping("/author/{id}") // 경로 수정
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable("id") Long id,
            @RequestParam("password") String password
    ) {
        authorService.deleteAuthor(id, password);
        return ResponseEntity.noContent().build(); // 상태 코드 204 반환
    }
}
