package scheduleapp.service;

import scheduleapp.dto.AuthorRequestDto;
import scheduleapp.dto.AuthorResponseDto;
import scheduleapp.dto.TaskRequestDto;


public interface AuthorService {
    AuthorResponseDto saveAuthor(AuthorRequestDto dto);
    AuthorResponseDto getAuthor(Long id, String password);  // 수정
    AuthorResponseDto updateAuthor(Long id, AuthorRequestDto dto, String password);
    void deleteAuthor(Long id, String password);
}
